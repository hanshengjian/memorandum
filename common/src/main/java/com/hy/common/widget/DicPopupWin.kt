package com.hy.common.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.hy.common.R
import com.hy.utils.UiUtil

/**
 * @auther:hanshengjian
 * @date:2021/12/13
 *
 */
class DicPopupWin(page: Int, context: Context?) : PopupWindow(context),View.OnClickListener {

    var mConverll:View ?=null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dic_popup, null, false)
        contentView = view
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = UiUtil.dip2px(context,500f)
        setOutsideTouchable(true)
        setFocusable(true)


        initView(view)

    }

    fun initView(root:View){
        mConverll = root.findViewById(R.id.cover_ll)
        root.findViewById<View>(R.id.pic_manager_tv).setOnClickListener(this)
    }



    fun show(view: View){
        showAsDropDown(view)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.pic_manager_tv ->{
                
            }
        }
    }
}