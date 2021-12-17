package com.tuyrt.myarch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.hi.dhl.binding.databind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.ext.showToolbar
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.ActivityTestBinding

/**
 * Created by tuyrt7 on 2021/12/14.
 * 说明：
 */
class TestActivity : BaseActivity() {

    private val binding: ActivityTestBinding by databind(R.layout.activity_test)

    private val viewModel: TestViewModel by viewModels()

    override fun initial(savedInstanceState: Bundle?) {
        binding.run {
            btnLogin.setOnClickListener {
                viewModel.login()
            }
            btnLoginWrong.setOnClickListener {
                viewModel.loginWithWrongPwd()
            }
            close.setOnClickListener { onBackPressed() }

            showToolbar(toolbar)
        }
    }

    override fun createObserver() {
        viewModel.loginLiveData.observeWater(this) {
            onStart {
                KLog.d("请求开始")
            }

            onSuccess {
                KLog.d("请求成功")
                toast("登录成功")

                binding.tvResult.text = it.toString()
            }
            onEmpty {
                KLog.d("数据为空")
                toast("数据为空")
            }

            onFailure {
                KLog.d("请求失败")
                binding.tvResult.text = it.toString()
            }

            onFinish {
                KLog.d("请求结束")
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TestActivity::class.java)
            context.startActivity(starter)
        }
    }
}