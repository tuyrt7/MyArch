package com.tuyrt.myarch.ui

import androidx.lifecycle.ViewModel
import com.tuyrt.architecture.capacity.network.StateLiveData
import com.tuyrt.architecture.ext.launchFlowAndCollect
import com.tuyrt.myarch.logic.data.entity.LoginModel
import com.tuyrt.myarch.logic.data.Repository

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
class TestViewModel : ViewModel() {

    val loginLiveData = StateLiveData<LoginModel>()
    fun login() {
        launchFlowAndCollect({ Repository.loginFlow("PuKxVxvMzBp2EJM") }) {
            loginLiveData.value = it
        }

    }

    fun loginWithWrongPwd() {
        launchFlowAndCollect({ Repository.loginFlow("123456") }) {
            loginLiveData.value = it
        }
    }

}

