package com.hy.common.widget;

import androidx.recyclerview.widget.DiffUtil;

/**
 * @auther:hanshengjian
 * @date:2021/12/30
 */
public class RecyclerItemCallback extends DiffUtil.Callback {

    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
