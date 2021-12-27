package com.tuyrt.myarch.test.binding.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.ext.showToolbar
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.ActivityTestViewbindBinding
import com.tuyrt.myarch.databinding.LayoutMergeItemBinding

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
class ViewBindingActivity : BaseActivity(R.layout.activity_test_viewbind) {

    private val binding: ActivityTestViewbindBinding by viewbind()

    @SuppressLint("SetTextI18n")
    override fun initial(savedInstanceState: Bundle?) {
        binding.run {
            btnTest.setOnClickListener {
                toast("绑定view click")
            }

            showToolbar(toolbar,"ViewBinding")

            tvResult.text = "Activity 使用 ViewBinding"

            // include without merge
            include.includeTvTitle.text = "使用 include 布局中的控件, 不包含 merge"
            // include
            LayoutMergeItemBinding.bind(root).mergeTvTitle.text = "使用 include 布局中的控件, 包含 merge"
        }
    }

    override fun createObserver() {
    }
}