package com.hy.bedone.widget

import com.hy.common.model.Bedone
import com.hy.common.widget.RecyclerDiffItemCallback

/**
 * @auther:hanshengjian
 * @date:2021/12/30
 */
class BedoneDiffItemCallback(olds: List<Bedone>, news: List<Bedone>) :
    RecyclerDiffItemCallback<Bedone>(olds, news) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return olds[oldItemPosition].id == news[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //解构声明
        val (id, content, type) = olds[oldItemPosition]
        val (id1, content1, type1) = news[newItemPosition]
        return if (id == id1 && content == content1 && type == type1) {
            true
        } else false
    }
}