package com.tuyrt.architecture.base.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 带有DataBinding的Dialog基类
 */
abstract class BaseBindingDialog<B : ViewDataBinding> : BaseDialog {
    protected lateinit var binding: B

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(savedInstanceState)
        refreshAttributes()
    }

    override fun initContentView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(getContext()),
            getLayoutId(), null, false
        )
        setContentView(binding.root)
    }
}