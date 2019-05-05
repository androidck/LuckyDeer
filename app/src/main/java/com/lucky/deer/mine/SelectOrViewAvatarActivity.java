package com.lucky.deer.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.demo.cloudstorage.executor.StartUpload;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.recognition.FileUtil;
import com.lucky.deer.util.ImageUtils;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.model.request.userinfo.MineInfoReq;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.lucky.model.util.ImgUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.BindView;

import static com.lucky.deer.weight.interfaces.OnItemClickListener.ClickStatus.OK;

/**
 * 查看或更换头像
 *
 * @author wangxiangyi
 * @date 2018/11/2
 */
public class SelectOrViewAvatarActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.ll_top_bar_background)
    LinearLayout llTopBarBackground;
    @BindView(R.id.tv_avatar_img)
    ImageView tvAvatarImg;
    private QMUIBottomSheet qmuiBottomSheet;
    private String absolutePath;
    private String mImagPath;
    private PublicDialog instance;

    @Override
    protected int initLayout() {
        return R.layout.activity_select_or_view_avatar;
    }

    @Override
    public boolean isStatusBarFontWhite() {
        return false;
    }

    @Override
    protected void initView() {
        /*设置背景颜色*/
        llTopBarBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack));
        /*设置标题和标题颜色*/
        topBar.setTitle(R.string.title_activity_select_or_view_avatar)
                .setTextColor(ContextCompat.getColor(this, R.color.white));
        /*设置标题背景颜色*/
        topBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack));
        /*返回按钮*/
        topBar.addLeftImageButton(R.mipmap.photo_back, 0)
                .setOnClickListener(v -> {
                    if (!TextUtils.isEmpty(mImagPath)) {
                        Intent intent = new Intent();
                        intent.putExtra(mEntity, mImagPath);
                        setResult(Activity.RESULT_OK, intent);
                    }
                    overridePendingTransition(false, true);
                });
        /*打开弹出框按钮*/
        topBar.addRightImageButton(R.mipmap.user_photo_more, 0)
                .setOnClickListener(v -> {
                    String[] getdata;
                    if (TextUtils.isEmpty(mImagPath)) {
                        getdata = getdata(R.array.list_select_or_view_avatar);
                    } else {
                        getdata = getdata(R.array.list_select_or_view_avatar2);
                    }
                    /*初始化视图*/
                    qmuiBottomSheet = instance.addHeaderView(instance.publicListView(mContext, getdata));
                    qmuiBottomSheet.show();
                });
        /*获取单利*/
        instance = PublicDialog.getInstance();
    }

    @Override
    protected void initData() {
        if (getStringData() != null) {
            mImagPath = getStringData();
            GlideUtils.loadImage(mContext, tvAvatarImg, mImagPath);
        }
    }

    @Override
    protected void initListener() {
        /*初始化选择监听*/
        instance.setOnItemClickListener((status, position) -> {
            qmuiBottomSheet.dismiss();
            if (status == OK) {
                switch (position) {
                    /*拍照*/
                    case 0:
                        absolutePath = FileUtil.getSaveFile(getApplication()).getAbsolutePath();
                        Intent intent = new Intent(mContext, CameraActivity.class);
                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, absolutePath);
                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                CameraActivity.CONTENT_TYPE_GENERAL);
                        startActivityForResult(intent, KeyConstant.SHOOTING_AVATAR);
//                        /*系统常量， 启动相机的关键*/
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, absolutePath);
//                        /*参数常量为自定义的request code, 在取返回结果时有用*/
//                        startActivityForResult(intent, CloudstorageKeyConstant.SHOOTING_AVATAR);
                        break;
                    /*相册选择*/
                    case 1:
                        mPermissions.setPermissionsRequest(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).subscribe(aBoolean -> {
                            if (aBoolean) {
                                startActivityForResult(
                                        ImgUtils.getInstance().selectLocalData(mContext, ImgUtils.IMAGE),
                                        KeyConstant.ALBUM_SELECT_AVATAR);
                            } else {
                                mPermissions.openSettingDetail();
                            }
                        });
                        break;
                    /*保存图片*/
                    case 2:
                        mPermissions.setPermissionsRequest(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).subscribe(aBoolean -> {
                            if (aBoolean) {
                                Bitmap bm = ((BitmapDrawable) ((ImageView) tvAvatarImg).getDrawable()).getBitmap();
                                boolean isSave = ImageUtils.saveImageToGallery(SelectOrViewAvatarActivity.this, bm);
                                if (isSave == true) {
                                    Toast.makeText(SelectOrViewAvatarActivity.this, "保存头像成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SelectOrViewAvatarActivity.this, "保存头像成功", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                mPermissions.openSettingDetail();
                            }
                        });
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*判断是否返回不成功*/
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                /*拍照图片*/
                case KeyConstant.SHOOTING_AVATAR:
                    uploadImage(absolutePath);
                    break;
                /*相册选择头像*/
                case KeyConstant.ALBUM_SELECT_AVATAR:
                    uploadImage(ImgUtils.getInstance().getImgPath(mContext, data));
                    break;
                default:
            }
        }
    }

    /**
     * 上传图片
     *
     * @param imagePath
     */
    @SuppressLint("CheckResult")
    private void uploadImage(String imagePath) {
        showLoadingDialog();
        StartUpload
                .getInstance()
                .upload(null, imagePath)
                .subscribe(baseReq -> {
                    if (TextUtils.isEmpty(baseReq.getKey())) {
                        dismissLoadingDialog();
                        HintUtil.showErrorWithToast(mContext, "图片上传失败！");
                        tvAvatarImg.setImageResource(R.mipmap.user_photo_no);
                    } else {
                        updateUserHead(HttpConstant.QINIU_URL + baseReq.getKey());
                    }
                });
    }

    /**
     * 上传或修改头像
     *
     * @param imagPath 头像地址
     */
    @SuppressLint("CheckResult")
    private void updateUserHead(String imagPath) {
        MineInfoReq mineInfoReq = new MineInfoReq();
        mineInfoReq.setUserHead(imagPath);
        mNetworkRequestInstance.updateUserHead(mineInfoReq)
                .subscribe(stringResponseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(stringResponseData)) {
                        mImagPath = imagPath;
                        GlideUtils.loadImage(mContext, tvAvatarImg, imagPath);
                        showSuccessDialog(R.string.successful_avatar_upload);
                    } else {
                        HintUtil.showErrorWithToast(mContext, stringResponseData.getMsg());
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!TextUtils.isEmpty(mImagPath)) {
            Intent intent = new Intent();
            intent.putExtra(mEntity, mImagPath);
            setResult(Activity.RESULT_OK, intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
