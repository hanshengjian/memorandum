package com.hy.common.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.hy.common.R
import com.hy.common.flutter.MemFlutterActivity
import com.hy.common.flutter.MemFlutterConstants

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
class DicManagerPopupWin(val page:Int,val context:Context):PopupWindow(context) {
    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.dic_manager_popup, null, false)
        contentView = rootView
        setBackgroundDrawable(BitmapDrawable())
        setOutsideTouchable(true)
        setFocusable(true)

        initView(rootView)
    }

    fun initView(root:View) {
        root.findViewById<View>(R.id.create_dic_tv).setOnClickListener {
            dismiss()
            val addDicTypePopupWin = AddDicTypePopupWin(page, context)
            val parentView = (context as Activity).window.decorView
            addDicTypePopupWin.show(parentView)
        }
        root.findViewById<View>(R.id.edit_dic_type_tv).setOnClickListener {
            dismiss()
            MemFlutterActivity.toFlutterPage(
                context,
                "main",
                MemFlutterConstants.FLUTTER_ENGINE_ID_VERTICAL
            )
        }
    }

    fun show(view:View){
       showAsDropDown(view)
    }
}