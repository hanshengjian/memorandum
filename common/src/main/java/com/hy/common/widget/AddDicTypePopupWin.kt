package com.hy.common.widget

import android.app.Activity
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import com.hy.common.R
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.utils.UiUtil
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.util.EventLog
import com.alibaba.android.arouter.launcher.ARouter
import com.hy.common.eventbus.CreateDicTypeEvent
import com.hy.common.navigator.DicManagerService
import org.greenrobot.eventbus.EventBus


/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
class AddDicTypePopupWin(val page:Int,val context: Context) : PopupWindow(context) {

    var contentEdit:EditText?=null

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.add_dic_type_dialog, null, false)
        contentView = rootView

        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        setBackgroundDrawable(BitmapDrawable())
        setOutsideTouchable(true)
        setFocusable(true)
        //给PopupWindow的window窗口设置软键盘展示属性
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView(rootView)
        popupInputMethodWindow()
    }


    /**
     * 弹出输入法窗口
     */
    private fun popupInputMethodWindow() {
        Handler().postDelayed(Runnable {
            val imm = contentEdit?.getContext()
                ?.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }, 200)
    }

    fun hideMethodWindow(){
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.hideSoftInputFromWindow(contentEdit?.getWindowToken(), 0);
        dismiss()
    }

    fun initView(root:View){
        root.findViewById<View>(R.id.cancel_tv).setOnClickListener {
            //收回软键盘
            hideMethodWindow()
        }
        contentEdit = root.findViewById(R.id.type_name_edit) as EditText
        contentEdit?.requestFocus()

        root.findViewById<View>(R.id.submit_tv).setOnClickListener {
            //掉用保存接口
            NavigatorManager.getNavigator(DicManagerNavigator::class.java)
                ?.getDicManager()?.addDicType(page,contentEdit?.text.toString()){ i,message->
                    Log.i("AddDicTypePopupWin","保存结果,$i" + message)
                    if(i > 0){
                        hideMethodWindow()
                        dismiss()
                        //刷新前一个文件夹
                        EventBus.getDefault().post(CreateDicTypeEvent())
                    }else{
                        //保存失败
                        dismiss()
                    }
                }
        }
    }

    fun show(view:View){
        showAtLocation(view,Gravity.BOTTOM,0,0)
       //showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }
}