package com.hy.note.widget

import com.hy.common.model.Note
import com.hy.common.widget.RecyclerDiffItemCallback

/**
 * @auther:hanshengjian
 * @date:2021/12/30
 */
class NoteDiffItemCallback(olds: List<Note>, news: List<Note>) :
    RecyclerDiffItemCallback<Note>(olds, news) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return olds[oldItemPosition].id == news[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (id, content, type, title) = olds[oldItemPosition]
        val (id1, content1, type1, title1) = news[newItemPosition]
        return if (id == id1 && content == content1 && type == type1 && title == title1) {
            true
        } else false
    }
}