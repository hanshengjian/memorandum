package com.hy.bedone

import android.view.View
import com.hy.bedone.databinding.FragmentBedoneListBinding
import com.hy.common.base.BaseFragment


/**
 * @Author Lenovo
 */
class BeDoneFragment:BaseFragment<FragmentBedoneListBinding>(){
    override fun onCreateLayout(): Int {
        return R.layout.fragment_bedone_list
    }

    override fun initView(root: View) {
    }

    override fun initData() {
    }
}