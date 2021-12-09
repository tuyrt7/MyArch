package com.tuyrt.myarch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.observeResponse
import com.tuyrt.architecture.capacity.network.observeState
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.databinding.ActivityMainBinding
import com.tuyrt.myarch.ext.showMessage
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val TAG = "aaaa"

    private val binding: ActivityMainBinding by viewbind()
    private val viewModel by viewModels<MainViewModel>()

    override fun initial(savedInstanceState: Bundle?) {

        binding.run {
            btnLogin.setOnClickListener {
                // viewModel.login()
                viewModel.loginFlow()
            }

            btnLoginWrong.setOnClickListener {
                //viewModel.loginWithWrongPwd()
                viewModel.loginWithWrongPwdFlow()
            }

            btnTestXk.setOnClickListener {
                viewModel.getKey()
            }
        }
    }

     override fun initObserver() {
        /*viewModel.loginLiveData.observeState(this) {
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
                KLog.d( "请求结束")
            }
        }*/


        viewModel.loginLiveData.observeResponse(this,false, onStart = {
            //LoadingDialog.show(this,"请稍后...")
            showMessage("你好哦你好哦你好哦")
        }, onFinish = {
            // LoadingDialog.dismiss(this)
            KLog.d("=========你好哦================")
        }) {
            binding.tvResult.text = it.toString()
        }

         lifecycleScope.launch {
             /*viewModel.loginFlow.observeState {
                 onSuccess {
                     binding.tvResult.text = it.toString()
                 }

                 onFailure {
                 KLog.d("observeState发生了错误： ${it.code} == ${it.errorMsg}")
                     binding.tvResult.text = it.toString()
                 }
             }*/

             viewModel.loginFlow.observeResponse(onFailure = {
                 KLog.d("observeResponse发生了错误： ${it.code} == ${it.errorMsg}")
                 binding.tvResult.text = it.toString()
             }) {
                 binding.tvResult.text = it.toString()
             }
         }

         viewModel.secretLiveData.observeState(this) {
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
                 KLog.d( "请求结束")
             }
         }
    }
}