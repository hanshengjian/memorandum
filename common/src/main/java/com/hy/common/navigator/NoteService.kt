package com.hy.common.navigator

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @auther:hanshengjian
 * @date:2021/12/28
 *
 */
interface NoteService : IProvider{
    fun getNoteSize(expression:(Int?,String?)->Unit)
}