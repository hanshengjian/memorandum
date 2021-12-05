package com.hy.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Author Lenovo
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: T

    fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(inflater, onCreateLayout(), container, false).apply {
        lifecycleOwner = this@BaseFragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        initData()
    }

    abstract fun onCreateLayout(): Int
    abstract fun initView(root: View)
    abstract fun initData()
}