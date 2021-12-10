package com.tuyrt.myarch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tuyrt.architecture.capacity.network.BaseResponse
import com.tuyrt.architecture.capacity.network.request
import com.tuyrt.architecture.ext.launchFlow
import com.tuyrt.myarch.logic.model.LoginModel
import com.tuyrt.myarch.logic.model.Secret
import com.tuyrt.myarch.logic.network.Repository
import kotlinx.coroutines.flow.*

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

    private val _loginFlow = MutableSharedFlow<BaseResponse<LoginModel>>(replay = 1)

    val loginFlow: SharedFlow<BaseResponse<LoginModel>> = _loginFlow

    fun loginFlow() {
        launchFlow {
            val asLiveData = Repository.loginFlow("PuKxVxvMzBp2EJM").asLiveData(viewModelScope.coroutineContext)
            Repository.loginFlow("PuKxVxvMzBp2EJM").collect {
                _loginFlow.tryEmit(it)
            }
        }
    }

    fun loginWithWrongPwdFlow() {
        launchFlow {
            Repository.loginFlow("123456").collect {
                _loginFlow.tryEmit(it)
            }
        }
    }

    val secretLiveData = MutableLiveData<BaseResponse<Secret>>()

    fun getKey() {
        launchFlow {
            Repository.getKey("123456").collect {
                secretLiveData.value = it
            }
        }
    }
}