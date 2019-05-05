package com.lucky.deer.find.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperInter mAdapter;

    public ItemTouchHelperCallback(ItemTouchHelperInter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//上下拖动
        int swipeFlag = ItemTouchHelper.LEFT;//从右向左
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemChange(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;//返回true 表示目标viewholder已经移到目标位置
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;//返回true支持长按拖动 false不支持
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;//返回true支持滑动 false不支持
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDelete(viewHolder.getAdapterPosition());
    }
}
