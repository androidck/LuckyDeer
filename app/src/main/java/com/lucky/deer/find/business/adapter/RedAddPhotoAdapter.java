package com.lucky.deer.find.business.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RedAddPhotoAdapter extends BaseRecyclerViewAdapter<RedAddPhotoAdapter.ViewHolder> implements ItemTouchHelperInter  {


    private Context context;
    private OnItemDelListener onItemDelListener;
    private List<String> imgUrl;

    public RedAddPhotoAdapter(Context context,List<String> list) {
        super(context);
        this.context=context;
        this.imgUrl=list;
        if (this.imgUrl==null){
            imgUrl=new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent,R.layout.item_red_add_photo);

    }

    @Override
    public int getItemCount() {
        return imgUrl==null?0:imgUrl.size();
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (imgUrl!=null){
            holder.itemImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(imgUrl.get(position)).into(holder.itemImg);
        }else {
            holder.itemImg.setVisibility(View.GONE);
        }
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDelListener.onDelClick(position);
            }
        });
    }

    @Override
    public void onItemChange(int fromPos, int toPos) {
        //当数据条数大于1 的时候才能交换位置
       if (imgUrl.size()>1){
           Collections.swap(imgUrl, fromPos, toPos);//调换数据源位置
           notifyItemMoved(fromPos, toPos);//调换条目位置
       }
    }

    @Override
    public void onItemDelete(int pos) {

    }


    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        ImageView itemImg,imgDel;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            itemImg= (ImageView) findViewById(R.id.item_img);
            imgDel= (ImageView) findViewById(R.id.img_del);
        }
    }

    //设置图片
    public void setImgUrl(List<String> imgUrl){
        this.imgUrl=imgUrl;
        notifyDataSetChanged();
    }

    public interface OnItemDelListener{
        void onDelClick(int position);
    }

    public void setOnItemDelListener(OnItemDelListener onItemDelListener) {
        this.onItemDelListener = onItemDelListener;
    }



}
