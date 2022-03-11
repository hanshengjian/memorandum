package com.hy.bedone

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.hy.bedone.databinding.FragmentBedoneListBinding
import com.hy.bedone.widget.BedoneDiffItemCallback
import com.hy.common.base.BaseFragment
import com.hy.common.eventbus.RefreshBedone
import com.hy.common.model.Bedone
import com.hy.common.widget.DicPopupWin
import kotlinx.android.synthetic.main.fragment_bedone_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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
    }

    override fun initData() {
        EventBus.getDefault().register(this)

        ElegantBus.getDefault("bedoneSaveState")
            .observe(this, object : ObserverWrapper<Any>() {
                override fun onChanged(value: Any?) {
                    if (value is Int) {
                        refreshData(value)
                    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(refreshBedone: RefreshBedone) {
        refreshData(refreshBedone.type.id)
    }


}