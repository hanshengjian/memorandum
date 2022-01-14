package com.hy.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author Lenovo
 * 强制使用DataBinding
 */
abstract class BaseActivity<T:ViewDataBinding> : AppCompatActivity(){
    lateinit var binding:T

    fun binding():T = DataBindingUtil.setContentView<T>(this,onCreateLayout()).apply {
        lifecycleOwner = this@BaseActivity
    }

    abstract fun onCreateLayout(): Int
    abstract fun initView()
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = binding()
        initView()
        initData()
    }


}