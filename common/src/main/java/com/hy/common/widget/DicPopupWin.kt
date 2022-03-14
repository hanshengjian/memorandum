package com.hy.common.widget

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hy.common.HyVariable
import com.hy.common.R
import com.hy.common.eventbus.CreateDicTypeEvent
import com.hy.common.model.DicType
import com.hy.common.navigator.BedoneNavigator
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
 * expression (code:Int) -1 全部，0 未分类 其他已分类
 * page: 0 笔记  1 代办
 */
class DicPopupWin(
    val page: Int,
    val context: Context?,
    val expression: (dicType: DicType?) -> Unit
) :
    PopupWindow(context), View.OnClickListener {

    var mConverll: View? = null
    var mDicListRecyc: RecyclerView? = null
    var mDicPopAdapter: DicPopAdapter? = null
    var mAllDataSizeTv: TextView? = null
    var mNoTypeTv: TextView? = null
    var mDeleteTypeTv: TextView? = null

    //label tv
    var allDataLabelTv: TextView? = null
    var labelMap: MutableMap<Int, String> = mutableMapOf(
        Pair(0, "全部笔记")
        , Pair(1, "全部待办")
    )


    init {
        EventBus.getDefault().register(this)
        val view = LayoutInflater.from(context).inflate(R.layout.dic_popup, null, false)
        contentView = view
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = UiUtil.dip2px(context, 500f)
        setBackgroundDrawable(BitmapDrawable())
        setOutsideTouchable(true)
        setFocusable(true)

        val drawable =
            context?.resources?.let { ColorDrawable(it.getColor(R.color.transparent_50)) }
        setBackgroundDrawable(drawable)
        initView(view)

    }

    fun initView(root: View) {
        mConverll = root.findViewById(R.id.cover_ll)
        mAllDataSizeTv = root.findViewById(R.id.all_data_tv)
        mNoTypeTv = root.findViewById(R.id.no_type_tv)
        allDataLabelTv = root.findViewById(R.id.all_data_lable_tv)
        mDeleteTypeTv = root.findViewById(R.id.delete_data_tv)
        if (page == 1) {
            mConverll?.visibility = View.GONE
            allDataLabelTv?.text = labelMap[page]
        }

        root.findViewById<View>(R.id.pic_manager_tv).setOnClickListener(this)
        root.findViewById<View>(R.id.all_data_rl).setOnClickListener(this)
        root.findViewById<View>(R.id.no_type_rl).setOnClickListener(this)
        root.findViewById<View>(R.id.delete_rl).setOnClickListener(this)

        mDicListRecyc = root.findViewById(R.id.dic_list_recyc)
        mDicListRecyc?.layoutManager = LinearLayoutManager(context)
        mDicPopAdapter = DicPopAdapter()
        mDicListRecyc?.adapter = mDicPopAdapter
        mDicListRecyc?.addOnItemTouchListener(
            ItemTouchHelper(context!!,
                object : ItemTouchHelper.OnItemTouchListenter {
                    override fun onItemClick(position: Int, childView: View?) {
                        dismiss()
                        val dicType = mDicPopAdapter?.dicTypes?.get(position)
                        expression.invoke(dicType)
                    }
                })
        )


        refreshDicList()
    }

    fun refreshDicList() {
        NavigatorManager.getNavigator(DicManagerNavigator::class.java)?.getDicManager()
            ?.getDicList(page) { dictypes, messsge ->
                dictypes?.let {
                    mDicPopAdapter?.dicTypes = dictypes
                    mDicPopAdapter?.notifyDataSetChanged()
                }
            }
        if (page == 0) {
            NavigatorManager.getNavigator(NoteNavigator::class.java)?.getNoteService()
                ?.getNoteSize { result, message ->
                    if (result != null && result > 0) {
                        mAllDataSizeTv?.text = result.toString()
                    }
                }

            NavigatorManager.getNavigator(NoteNavigator::class.java)?.getNoteService()
                ?.getNotesSizeNoType { result, message ->
                    if (result != null && result > 0) {
                        mNoTypeTv?.text = result.toString()
                    }
                }
            NavigatorManager.getNavigator(NoteNavigator::class.java)?.getNoteService()
                ?.getDeletedNoteSize { result, message ->
                    if (result != null && result > 0) {
                        mDeleteTypeTv?.text = result.toString()
                    }
                }


        } else {
            NavigatorManager.getNavigator(BedoneNavigator::class.java)?.getBedoneService()
                ?.getBedoneSize { result, message ->
                    if (result != null && result > 0) {
                        mAllDataSizeTv?.text = result.toString()
                    }
                }
            NavigatorManager.getNavigator(BedoneNavigator::class.java)?.getBedoneService()
                ?.getBedoneSizeNoType { result, message ->
                    if (result != null && result > 0) {
                        mNoTypeTv?.text = result.toString()
                    }
                }
            NavigatorManager.getNavigator(BedoneNavigator::class.java)?.getBedoneService()
                ?.getDeletedBedoneSize() { result, message ->
                    if (result != null && result > 0) {
                        mDeleteTypeTv?.text = result.toString()
                    }
                }
        }

    }


    fun show(view: View) {
        showAsDropDown(view)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pic_manager_tv -> {
                val dicPopupWin = DicManagerPopupWin(page, context!!)
                dicPopupWin.show(v)
            }
            R.id.all_data_rl -> {
                dismiss()
                expression.invoke(
                    DicType(
                        id = HyVariable.ALL_TYPE,
                        content = labelMap[page]!!,
                        page = this.page
                    )
                )
            }
            R.id.no_type_rl -> {
                dismiss()
                expression.invoke(DicType(id = HyVariable.NO_TYPE, content = "未分类"))
            }
            R.id.delete_rl -> {
                dismiss()
                expression.invoke(DicType(id = HyVariable.DELETE_TYPE, content = "最近删除"))
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(event: CreateDicTypeEvent) {
        refreshDicList()
    }

    override fun dismiss() {
        super.dismiss()
        EventBus.getDefault().unregister(this)
    }
}