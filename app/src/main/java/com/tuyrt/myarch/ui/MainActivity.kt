package com.tuyrt.myarch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.observeWater
import com.tuyrt.architecture.capacity.network.observeWaterResponse
import com.tuyrt.architecture.ext.goActivity
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.ActivityMainBinding
import com.tuyrt.myarch.test.banner.BannerActivity
import com.tuyrt.myarch.test.viewbinding.DataBindingActivity
import com.tuyrt.myarch.test.viewbinding.ViewBindingActivity
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewbind()
    private val viewModel: MainViewModel by viewModels()

    override fun initial(savedInstanceState: Bundle?) {

        binding.run {
            btnLogin.setOnClickListener {
                viewModel.login()
                // viewModel.loginFlow()
            }

            btnLoginWrong.setOnClickListener {
                viewModel.loginWithWrongPwd()
                // viewModel.loginWithWrongPwdFlow()
            }

            btnTestXk.setOnClickListener {
                viewModel.getKey()
            }

            btnGo.setOnClickListener { goActivity<TestActivity>() }
            btnGoViewBind.setOnClickListener { goActivity<ViewBindingActivity>() }
            btnGoDataBind.setOnClickListener { goActivity<DataBindingActivity>() }
            btnGoBanner.setOnClickListener { goActivity<BannerActivity>() }
        }

    }

    override fun createObserver() {
//        viewModel.loginLiveData.observeFire(this) {
//            onStart {
//                KLog.d("请求开始")
//            }
//
//            onSuccess {
//                KLog.d("请求成功")
//                toast("登录成功")
//
//                binding.tvResult.text = it.toString()
//            }
//            onEmpty {
//                KLog.d("数据为空")
//                toast("数据为空")
//            }
//
//            onFailure {
//                KLog.d("请求失败")
//                binding.tvResult.text = it.toString()
//            }
//
//            onFinish {
//                KLog.d("请求结束")
//            }
//        }

        viewModel.loginLiveData.observeFireResponse(this, false, onStart = {
            showLoading("加载中...")
            //showMessage("你好哦你好哦你好哦")
        }, onFinish = {
            dismissLoading()
            KLog.d("=========你好哦================")
        }) {
            binding.tvResult.text = it.toString()
        }

        lifecycleScope.launch {
            viewModel.loginFlow.observeWater {
                onSuccess {
                    binding.tvResult.text = it.toString()
                }

                onFailure {
                    KLog.d("observeState发生了错误： ${it.code} == ${it.errorMsg}")
                    binding.tvResult.text = it.toString()
                }
            }

             /*viewModel.loginFlow.observeWaterResponse(onFailure = {
                 KLog.d("observeResponse发生了错误： ${it.code} == ${it.errorMsg}")
                 binding.tvResult.text = it.toString()
             }) {
                 binding.tvResult.text = it.toString()
             }*/
        }

        viewModel.secretLiveData.observeWater(this) {
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
}