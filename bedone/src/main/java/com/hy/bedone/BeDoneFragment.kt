package com.hy.bedone

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hy.bedone.databinding.FragmentBedoneListBinding
import com.hy.common.base.BaseFragment
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
                refreshData(type?.id)
            }
            dicPopupWindow!!.setOnDismissListener {
                bedone_arrow_iv.setImageResource(R.mipmap.arrow_up_bold)
            }
            dicPopupWindow!!.show(it)
        }

//        bedoneVieModel.deleteNote.observe(this, Observer {
//            if (it != null) {
//                val olds = noteListAdapter.notes?.toMutableList()
//                noteListAdapter.notes?.remove(it)
//                val recycItemCallback =
//                    RecyclerDiffItemCallback(
//                        olds,
//                        noteListAdapter.notes
//                    )
//                val diffResult = DiffUtil.calculateDiff(recycItemCallback, true)
//                diffResult.dispatchUpdatesTo(noteListAdapter)
//                noteListAdapter.notes?.isEmpty()?.let { it1 -> emptyView(it1) }
//            } else {
//                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    override fun initData() {
    }

    fun refreshData(type: Int?) {
        if (type != null) {
            bedoneVieModel.getBedones(type)
        }
    }
}