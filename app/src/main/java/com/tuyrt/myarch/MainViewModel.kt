package com.tuyrt.myarch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuyrt.architecture.capacity.network.BaseResponse
import com.tuyrt.architecture.capacity.network.request
import com.tuyrt.myarch.logic.model.LoginModel
import com.tuyrt.myarch.logic.network.Repository

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
class MainViewModel : ViewModel() {

    val loginLiveData = MutableLiveData<BaseResponse<LoginModel>>()

    fun login() {
        loginLiveData.request(this) {
            Repository.login("PuKxVxvMzBp2EJM")
        }
    }

    fun loginWithWrongPwd() {
        loginLiveData.request(this) {
            Repository.login("123456")
        }
    }

    fun loginFlow() {

    }
}