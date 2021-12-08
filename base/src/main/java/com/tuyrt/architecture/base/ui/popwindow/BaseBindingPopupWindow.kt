package com.tuyrt.architecture.base.ui.popwindow

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 带有DataBinding的PopupWindow基类
 */
abstract class BaseBindingPopupWindow<B : ViewDataBinding>(context: Context) :
    BasePopupWindow(context) {

    protected lateinit var binding: B

    override fun initContentView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            getLayoutId(), null, false
        )
        contentView = binding.root
    }
}