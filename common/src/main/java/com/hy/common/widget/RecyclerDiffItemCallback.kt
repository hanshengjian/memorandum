package com.hy.common.widget

import androidx.recyclerview.widget.DiffUtil

/**
 * @auther:hanshengjian
 * @date:2022/2/28
 *
 */
open class RecyclerDiffItemCallback<T>(val olds: List<T>, val news: List<T>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return olds.size
    }

    override fun getNewListSize(): Int {
        return news.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false;
    }
}