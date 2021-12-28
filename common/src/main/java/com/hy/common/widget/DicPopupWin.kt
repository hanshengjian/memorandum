package com.hy.common.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.hy.common.R
import com.hy.common.eventbus.CreateDicTypeEvent
import com.hy.common.model.DicType
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.common.navigator.NoteNavigator
import com.hy.utils.UiUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @auther:hanshengjian
 * @date:2021/12/13
 *
 */
class DicPopupWin(val page: Int, val context: Context?,val expression:(Int?) -> Unit) : PopupWindow(context),View.OnClickListener {

    var mConverll:View ?=null
    var mDicListRecyc:RecyclerView ?=null
    var mDicPopAdapter:DicPopAdapter ?=null
    var mAllDataSizeTv:TextView ?=null

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
        mAllDataSizeTv = root.findViewById(R.id.all_data_tv)

        root.findViewById<View>(R.id.pic_manager_tv).setOnClickListener(this)
        root.findViewById<View>(R.id.all_data_rl).setOnClickListener(this)

        mDicListRecyc = root.findViewById(R.id.dic_list_recyc)
        mDicListRecyc?.layoutManager = LinearLayoutManager(context)
        mDicPopAdapter =  DicPopAdapter()
        mDicListRecyc?.adapter = mDicPopAdapter
        mDicListRecyc?.addOnItemTouchListener(ItemTouchHelper(context!!,object:ItemTouchHelper.OnItemTouchListenter{
            override fun onItemClick(position: Int, childView: View?) {
                dismiss()
                val dicType = mDicPopAdapter?.dicTypes?.get(position)
                expression.invoke(dicType?.id)
            }
        }))


        refreshDicList()
    }

    fun refreshDicList(){
        NavigatorManager.getNavigator(DicManagerNavigator::class.java)?.getDicManager()?.getDicList(page){
                dictypes,messsge->
            dictypes?.let {
                mDicPopAdapter?.dicTypes = dictypes
                mDicPopAdapter?.notifyDataSetChanged()
            }
        }

        NavigatorManager.getNavigator(NoteNavigator::class.java)?.getNoteService()?.getNoteSize{
            result,message ->
            if(result!=null && result>0){
                mAllDataSizeTv?.text = result.toString()
            }
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
            R.id.all_data_rl ->{
                dismiss()
                expression.invoke(0)
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