package com.hy.common.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.hy.common.R
import com.hy.common.eventbus.CreateDicTypeEvent
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.utils.UiUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @auther:hanshengjian
 * @date:2021/12/13
 *
 */
class DicPopupWin(val page: Int, val context: Context?) : PopupWindow(context),View.OnClickListener {

    var mConverll:View ?=null
    var mDicListRecyc:RecyclerView ?=null
    var mDicPopAdapter:DicPopAdapter ?=null

    init {
        EventBus.getDefault().register(this)
        val view = LayoutInflater.from(context).inflate(R.layout.dic_popup, null, false)
        contentView = view
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = UiUtil.dip2px(context,500f)
        setOutsideTouchable(true)
        setFocusable(true)

        val drawable = context?.resources?.let { ColorDrawable(it.getColor(R.color.transparent_50)) }
        setBackgroundDrawable(drawable)
        initView(view)

    }

    fun initView(root:View){
        mConverll = root.findViewById(R.id.cover_ll)
        root.findViewById<View>(R.id.pic_manager_tv).setOnClickListener(this)

        mDicListRecyc = root.findViewById(R.id.dic_list_recyc)
        mDicListRecyc?.layoutManager = LinearLayoutManager(context)
        mDicPopAdapter =  DicPopAdapter()
        mDicListRecyc?.adapter = mDicPopAdapter

        refreshDicList()
    }

    fun refreshDicList(){
        NavigatorManager.getNavigator(DicManagerNavigator::class.java)?.getDicManager()?.getDicList(page){
                dictypes,messsge->
            mDicPopAdapter?.dicTypes = dictypes
            mDicPopAdapter?.notifyDataSetChanged()
        }
    }



    fun show(view: View){
        showAsDropDown(view)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.pic_manager_tv ->{
                val dicPopupWin = DicManagerPopupWin(page,context!!)
                dicPopupWin.show(v)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(event:CreateDicTypeEvent){
        refreshDicList()
    }

    override fun dismiss() {
        super.dismiss()
        EventBus.getDefault().unregister(this)
    }
}