package com.tuyrt.architecture.base.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 带有DataBinding的DialogFragment基类
 */
abstract class BaseBindingDialogFragment<B : ViewDataBinding> : BaseDialogFragment() {
    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        rootView = binding.root
        return rootView
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getDialogStyle(): Int? {
        return null
    }

    override fun getDialogTheme(): Int? {
        return null
    }
}