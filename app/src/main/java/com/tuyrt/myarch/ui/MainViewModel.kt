package com.tuyrt.myarch.ui

import androidx.lifecycle.ViewModel
import com.tuyrt.architecture.capacity.network.data.BaseResponse
import com.tuyrt.architecture.capacity.network.StateLiveData
import com.tuyrt.architecture.capacity.network.request
import com.tuyrt.architecture.ext.launchFlowAndCollect
import com.tuyrt.myarch.logic.data.entity.LoginModel
import com.tuyrt.myarch.logic.data.entity.SecretModel
import com.tuyrt.myarch.logic.data.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */
class MainViewModel : ViewModel() {

    val loginLiveData = StateLiveData<LoginModel>()

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
        launchFlowAndCollect({ Repository.loginFlow("PuKxVxvMzBp2EJM")}) {
            _loginFlow.tryEmit(it)
        }
    }

    fun loginWithWrongPwdFlow() {
        launchFlowAndCollect({ Repository.loginFlow("123456")}) {
            _loginFlow.tryEmit(it)
        }
    }

    //  Flow + LiveData
    val secretLiveData = StateLiveData<SecretModel>()

    fun getKey() {
        launchFlowAndCollect({ Repository.getKeyFlow("123456")}){
            secretLiveData.value = it
        }
    }
}