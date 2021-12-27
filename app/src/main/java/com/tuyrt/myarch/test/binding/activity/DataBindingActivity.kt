package com.tuyrt.myarch.test.binding.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewStubProxy
import com.hi.dhl.binding.databind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.ext.showToolbar
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.ActivityTestDatabindBinding
import com.tuyrt.myarch.databinding.ViewStubBinding
import com.tuyrt.myarch.test.binding.model.User

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
class DataBindingActivity : BaseActivity() {

    private val binding: ActivityTestDatabindBinding by databind(R.layout.activity_test_databind) {
        title = "DataBinding"
    }

    @SuppressLint("SetTextI18n")
    override fun initial(savedInstanceState: Bundle?) {
        binding.run {
            btnTest.setOnClickListener {
                toast("绑定view click")
            }

            showToolbar(toolbar, "DataBinding")

            // ViewStub
            binding.viewStub.setOnInflateListener { stub, inflated ->
                // DataBinding
                val viewStub: ViewStubBinding = DataBindingUtil.bind(inflated)!!
                // ViewBinding
                //val viewStub2: ViewStubBinding = ViewStubBinding.bind(inflated)
                viewStub.tvTitle.text = "使用 ViewStub 加载 Binding 布局"
            }
            // 填充
            inflateLayout(binding.viewStub)


            // include 布局
            user = User(name = "数据绑定：dhl", account = "数据绑定：公众号：ByteCode")
            // DataBinding or ViewBinding ,使用 include 布局中的控件, 不包含 merge
            includeData.includeTvTitle.text = "用 include 布局中的控件, 不包含 merge"

            // include + Merge
            // 布局是 DataBinding
            includeDataMerge.mergeTvTitle.text = "DataBinding 使用 include 布局中的控件, 包含 merge"
            // 布局是 ViewBinding
            // LayoutMergeDataItemBinding.bind(binding.root).mergeTvTitle.text = "ViewBinding 使用 include 布局中的控件, 包含 merge"
        }
    }

    private fun inflateLayout(viewStubProxy: ViewStubProxy) {
        if (!viewStubProxy.isInflated) {
            viewStubProxy.viewStub!!.inflate()
        }
    }

    override fun createObserver() {

    }
}