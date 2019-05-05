package com.lucky.deer.find.business.adapter;

public interface ItemTouchHelperInter {
    //调换位置
    void onItemChange(int fromPos, int toPos);
    //滑动删除条目
    void onItemDelete(int pos);
}
