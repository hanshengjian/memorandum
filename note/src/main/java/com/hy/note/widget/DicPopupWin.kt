package com.hy.note.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hy.common.model.DicType
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.common.widget.DicPopAdapter
import com.hy.common.widget.ItemTouchHelper
import com.hy.note.R
import com.hy.utils.UiUtil

class DicPopupWin(val page:Int,val context:Context,val expression:(DicType?)->Unit): PopupWindow(context) {
    var mDicListRecyc: RecyclerView?=null
    var mDicPopAdapter: DicPopAdapter?=null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dic_popup_note,null,false)
        contentView = view

        this.width = UiUtil.dip2px(context,200f)
        this.height = UiUtil.dip2px(context,400f)

        setOutsideTouchable(true)
        setFocusable(true)

        initView(view)
    }

    fun initView(root: View){

        mDicListRecyc = root.findViewById(com.hy.common.R.id.dic_list_recyc)
        mDicListRecyc?.layoutManager = LinearLayoutManager(context)
        mDicPopAdapter =  DicPopAdapter()
        mDicListRecyc?.adapter = mDicPopAdapter
        mDicListRecyc?.addOnItemTouchListener(ItemTouchHelper(context,object:ItemTouchHelper.OnItemTouchListenter{
            override fun onItemClick(position: Int, childView: View?) {
                dismiss()
                val dicType = mDicPopAdapter?.dicTypes?.get(position)
                expression.invoke(dicType)
            }
        }))

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

}

