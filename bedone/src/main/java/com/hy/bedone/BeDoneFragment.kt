package com.hy.bedone

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.hy.bedone.databinding.FragmentBedoneListBinding
import com.hy.bedone.widget.BedoneDiffItemCallback
import com.hy.common.base.BaseFragment
import com.hy.common.model.Bedone
import com.hy.common.widget.DicPopupWin
import kotlinx.android.synthetic.main.fragment_bedone_list.*


/**
 * @Author Lenovo
 */
class BeDoneFragment:BaseFragment<FragmentBedoneListBinding>() {
    companion object {
        const val TAG = "BeDoneFragment"
    }

    lateinit var bedoneVieModel: BeDoneViewModel
    var dicPopupWindow: DicPopupWin? = null
    private val bedoneAdapter by lazy {
        BedoneAdapter()
    }


    override fun onCreateLayout(): Int {
        return R.layout.fragment_bedone_list
    }

    override fun initView(root: View) {
        bedoneVieModel = ViewModelProvider(this).get(BeDoneViewModel::class.java)
        binding.apply {
            viewModel = bedoneVieModel
            adapter = bedoneAdapter
        }

        dic_bedone_ll.setOnClickListener {
            // if(dicPopupWindow==null){
            bedone_arrow_iv.setImageResource(R.mipmap.arrow_down_bold)
            dicPopupWindow = DicPopupWin(1, activity) { type ->
                //重新刷新数据
                type_name_tv.text = type?.content
                bedoneVieModel.type = type?.id!!
                refreshData(type?.id)
            }
            dicPopupWindow!!.setOnDismissListener {
                bedone_arrow_iv.setImageResource(R.mipmap.arrow_up_bold)
            }
            dicPopupWindow!!.show(it)
        }


    }

    override fun initData() {
        bedoneVieModel.saveSuccess.observe(this, Observer {
            if (it != null) {
                //刷新列表
                bedoneAdapter.bedones?.add(0, it)
                bedoneAdapter.notifyItemInserted(0)
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show()
            }
        })
        bedoneVieModel.bedonesLiveData.observe(this, Observer {
            bedoneAdapter.apply {
                if (it.isNullOrEmpty()) {
                    clearData()
                    notifyDataSetChanged()
                } else {
                    val olds = if (bedones == null) mutableListOf() else bedones
                    diffRefresh(olds!!, it as MutableList<Bedone>)
                    bedones = it
                }
                emptyView(it.isEmpty())
            }
        })
        refreshData(0)
    }

    fun refreshData(type: Int?) {
        if (type != null) {
            bedoneVieModel.getBedones(type)
        }
    }

    fun diffRefresh(olds: MutableList<Bedone>, news: MutableList<Bedone>) {
        val recycItemCallback =
            BedoneDiffItemCallback(
                olds,
                news
            )
        val diffResult = DiffUtil.calculateDiff(recycItemCallback, true)
        diffResult.dispatchUpdatesTo(bedoneAdapter)
    }

    fun emptyView(empty: Boolean) {
        if (empty) {
            ll_empty.visibility = View.VISIBLE
        } else {
            ll_empty.visibility = View.GONE
        }
    }

}