package com.lucky.deer.mine.video;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dykj.requestcore.util.PermissionsUtils;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.mine.adapter.VideoListAdapter;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.common.ListPopupEntity;
import com.lucky.model.common.WebViewBean;
import com.lucky.model.request.BaseReq;
import com.lucky.model.request.find.PostVideoReq;
import com.lucky.model.response.find.VideoListEntity;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.RxBus;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.yhsj.loadstatusview.LoadStatusView;

/**
 * 我的视频
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class MyVideoActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.lsv_load_status)
    LoadStatusView lsvLoadStatus;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    /**
     * 批量选择按钮
     */
    @BindView(R.id.ll_batch_selection)
    LinearLayout llBatchSelection;
    /**
     * 全选按钮
     */
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    private VideoListAdapter mAdapter;
    private QMUIListPopup qmuiListPopup;
    private List<VideoListEntity> list;
    private PublicDialog inistanceView;
    private QMUIDialog qmuiDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_video;
    }

    @Override
    protected void initView() {
        initTopBar(topBar,
                R.string.title_activity_my_video,
                R.mipmap.video_more,
                v -> {
                    qmuiListPopup.show(v);
                });
        /*视频列表初始化*/
        rvList.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAdapter = new VideoListAdapter();
        rvList.setAdapter(mAdapter);
        inistanceView = PublicDialog.getInstance();
        /*菜单信息*/
        List<ListPopupEntity> strings = new ArrayList<>();
        ListPopupEntity uploadVideo = new ListPopupEntity();
        uploadVideo.setImg(R.mipmap.video_upload);
        uploadVideo.setText("上传视频");
        ListPopupEntity batchDeletion = new ListPopupEntity();
        batchDeletion.setImg(R.mipmap.video_batch);
        batchDeletion.setText("批量删除");
        strings.add(uploadVideo);
        strings.add(batchDeletion);
        /*初始化选择框*/
        qmuiListPopup = PublicDialog.getInstance().setListPopup(mContext,
                QMUIListPopup.DIRECTION_BOTTOM,
                PublicDialog
                        .getInstance()
                        .videoFeaturesAdapter(mContext,
                                strings),
                (parent, view, position, id) -> {
                    /*判断是否选择上传视频*/
                    if (position == 0) {
                        /*获取视频权限*/
                        PermissionsUtils
                                .getInstance(mContext)
                                .setPermissionsRequest(Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(aBoolean -> {
                                    if (!aBoolean) {
                                        PermissionsUtils
                                                .getInstance(mContext)
                                                .openSettingDetail();
                                    } else {
                                        /*上传视频*/
                                        jumpActivity(mContext, VideoActivity.class);
                                    }
                                });
                    } else {
                        if (lsvLoadStatus.getViewState() == LoadStatusView.VIEW_STATE_CONTENT) {
                            isDatchDeletion(true);
                        } else {
                            HintUtil.showErrorWithToast(mContext, "目前还没有视频");
                        }
                    }
                    qmuiListPopup.dismiss();
                });
        /*通知*/
        RxBus.getInstance()
                .toObservable()
                .subscribe(s -> {
                    if (KeyConstant.POST_VIDEO.equals(s)) {
                        initData();
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        BaseReq baseReq = new BaseReq();
        if (srlRefresh.isRefreshing()) {
            pageCurrent = 1;
            baseReq.setPageCurrent(pageCurrent);
        } else if (pageCurrent > 1) {
            baseReq.setPageCurrent(pageCurrent);
        } else {
            showLoadingDialog();
        }
        mNetworkRequestInstance.listVideos(baseReq)
                .subscribe(returnData -> {
                    lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                    dismissLoadingDialog();
                    srlRefresh.setRefreshing(false);
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        list = returnData.getData().getList();
                        if (list != null) {
                            if (baseReq.getPageCurrent() == 1) {
                                mAdapter.setNewData(list);
                                if (list.size() >= baseReq.getPageSize()) {
                                    pageCurrent++;
                                } else {
                                    /*加载结束*/
                                    mAdapter.loadMoreEnd(true);
                                }
                            } else if (list.size() >= baseReq.getPageSize()) {
                                mAdapter.addData(list);
                                /*加载完成*/
                                mAdapter.loadMoreComplete();
                                pageCurrent++;
                            } else {
                                mAdapter.addData(list);
                                /*加载结束*/
                                mAdapter.loadMoreEnd();
                            }
                        }
                    } else if (RequestUtils.isEmpty(returnData)) {
                        mAdapter.notifyDataSetChanged();
                        lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                    } else {
                        if (pageCurrent > 1) {
                            /*加载失败*/
                            mAdapter.loadMoreFail();
                        }
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }

    @Override
    protected void initListener() {
        /*下拉刷新监听*/
        srlRefresh.setOnRefreshListener(this::initData);
        /*上啦加载监听*/
        mAdapter.setOnLoadMoreListener(this::initData, rvList);
        /*视频点击监听*/
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (llBatchSelection.getVisibility() == View.GONE) {
                WebViewBean webViewBean = new WebViewBean();
                webViewBean.setPageType(KeyConstant.MY_VIDEO);
                webViewBean.setWebTitle(mAdapter.getData().get(position).getTitle());
                webViewBean.setContent(mAdapter.getData().get(position).getDesc());
                webViewBean.setWebUrl(HttpConstant.H5_WATCH_VIDEO + mAdapter.getData().get(position).getId());
                jumpActivity(mContext, WebViewActivity.class, webViewBean);
            }
        });
        /*选择删除视频监听*/
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (mAdapter.getData().get(position).isChecked()) {
                mAdapter.getData().get(position).setChecked(false);
            } else {
                mAdapter.getData().get(position).setChecked(true);
            }
            getSelectNumber();
        });
        inistanceView.setOnClickListener((OnClickListener<String>) (status, useType,isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    StringBuffer stringBuffer = new StringBuffer();
                    /*遍历要删除的视频*/
                    for (VideoListEntity videoListEntity : list) {
                        if (videoListEntity.isChecked()) {
                            stringBuffer.append(videoListEntity.getId());
                            stringBuffer.append(",");
                        }
                    }
                    qmuiDialog.dismiss();
                    /*截取最后一位并提交删除接口*/
                    delectVideo(stringBuffer.toString().substring(0, stringBuffer.length() - 1));
                    break;
                default:
            }
        });

    }

    @OnClick({R.id.ll_select_all, R.id.ll_delete, R.id.ll_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*全选*/
            case R.id.ll_select_all:
                if (getString(R.string.select_all).equals(tvSelectAll.getText().toString())) {
                    for (VideoListEntity videoListEntity : list) {
                        videoListEntity.setChecked(true);
                    }
                    getSelectNumber();
                } else {
                    for (VideoListEntity videoListEntity : list) {
                        videoListEntity.setChecked(false);
                    }
                    getSelectNumber();
                }
                mAdapter.notifyDataSetChanged();
                break;
            /*删除*/
            case R.id.ll_delete:
                if (getDeleteVideo()) {
                    /*初始化是否删除视频提示框*/
                    qmuiDialog = inistanceView.setCustomizeView(
                            inistanceView.initTitleEtOrTvView(mActivity,
                                    "提示",
                                    "确定要删除视频吗？",
                                    true),
                            KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_4);
                } else {
                    HintUtil.showErrorWithToast(mContext, "请选择要删除的视频");
                }
                break;
            /*取消*/
            case R.id.ll_cancel:
                isDatchDeletion(false);
                break;
            default:
        }
    }

    /**
     * 删除视频
     *
     * @param videoID 要删除的视频id
     */
    @SuppressLint("CheckResult")
    private void delectVideo(String videoID) {
        PostVideoReq postVideoReq = new PostVideoReq();
        postVideoReq.setId(videoID);
        showLoadingDialog();
        mNetworkRequestInstance.deleteVideo(postVideoReq)
                .subscribe(returnData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        /*获取所有视频信息*/
                        List<VideoListEntity> allVideoData = mAdapter.getData();
                        if (allVideoData != null && allVideoData.size() > 0) {
                            for (int i = allVideoData.size() - 1; i >= 0; i--) {
                                if (allVideoData.get(i).isChecked()) {
                                    mAdapter.remove(i);
                                }
                            }
                        }
                        showSuccessDialog(R.string.dialog_video_deleted_success);
                        RxBus.getInstance().post();
                        isDatchDeletion(false);
                        if (mAdapter.getItemCount()<=0) {
                            lsvLoadStatus.setViewState(LoadStatusView.VIEW_STATE_EMPTY);
                        }
                    } else {
                        HintUtil.showErrorWithToast(mContext, returnData.getMsg());
                    }
                });
    }


    /**
     * 获取要删除的视频
     *
     * @return 是否删除
     */
    private boolean getDeleteVideo() {
        /*遍历要删除的视频*/
        for (VideoListEntity videoListEntity : list) {
            if (videoListEntity.isChecked()) {
                return videoListEntity.isChecked();
            }
        }
        return false;
    }

    /**
     * 是否是批量删除
     *
     * @param isDatchDeletion true：是批量删除
     *                        false：不是批量删除
     */
    private void isDatchDeletion(boolean isDatchDeletion) {
        llBatchSelection.setVisibility(isDatchDeletion ? View.VISIBLE : View.GONE);
        mAdapter.isShowMultipleSelection(isDatchDeletion);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 计算选中个数
     */
    public void getSelectNumber() {
        int number = 0;
        /*遍历要删除的视频*/
        for (VideoListEntity videoListEntity : list) {
            if (videoListEntity.isChecked()) {
                number += 1;
            }
        }
        if (number != 0 && list.size() == number) {
            tvSelectAll.setText(R.string.cancel_all);
        } else {
            tvSelectAll.setText(R.string.select_all);
        }
    }
}
