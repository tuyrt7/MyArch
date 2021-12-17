package com.tuyrt.myarch.test.viewbinding

import android.os.Bundle
import com.hi.dhl.binding.databind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.ext.showToolbar
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.ActivityTestDatabindBinding

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
class DataBindingActivity : BaseActivity() {

    private val binding: ActivityTestDatabindBinding by databind(R.layout.activity_test_databind) {
        title  = "DataBinding"
    }

    override fun initial(savedInstanceState: Bundle?) {
        binding.run {
            btnTest.setOnClickListener {
                toast("绑定view click")
            }

            showToolbar(toolbar,"DataBinding")
        }
    }

    override fun createObserver() {

    }
}