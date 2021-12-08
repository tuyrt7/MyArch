package com.tuyrt.architecture.capacity.network

import androidx.lifecycle.*
import com.tuyrt.architecture.base.arch.BaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：
 */


/**
 * 监听 Flow 的值的变化，回调为 DSL 的形式
 */
suspend inline fun  <T> Flow<BaseResponse<T>>.observeState(
    isShowLoading: Boolean = true,
    isShowErrorToast: Boolean = true,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    this.collect { response->
        when (response) {
            is StartResponse -> {
                if (isShowLoading) {
                    BaseApp.eventViewModel.showDialog.call()
                }
                requestCallback.startCallback?.invoke()
            }
        }
    }
}


/**
 * 监听 LiveData 的值的变化，回调为 DSL 的形式
 */
inline fun <T> LiveData<BaseResponse<T>>.observeState(
    owner: LifecycleOwner,
    isShowLoading: Boolean = true,
    isShowErrorToast: Boolean = true,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {

    val requestCallback = HttpRequestCallback<T>().apply(callback)

    observe(owner, object : IStateObserver<T> {
        override fun onStart() {
            if (isShowLoading) {
                BaseApp.eventViewModel.showDialog.call()
            }
            requestCallback.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            requestCallback.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            requestCallback.emptyCallback?.invoke()
        }

        override fun onFailure(e: RequestException) {
            if (isShowErrorToast) {
                BaseApp.eventViewModel.toast(e.errorMsg)
            }
            requestCallback.failureCallback?.invoke(e)
        }

        override fun onFinish() {
            if (isShowLoading) {
                BaseApp.eventViewModel.dismissLoading()
            }
            requestCallback.finishCallback?.invoke()
        }
    })
}

/**
 * 监听 LiveData 的值的变化
 */
inline fun <T> LiveData<BaseResponse<T>>.observeResponse(
    owner: LifecycleOwner,
    isShowLoading: Boolean = true,
    crossinline onStart: OnUnitCallback = {},
    crossinline onEmpty: OnUnitCallback = {},
    crossinline onFailure: OnFailureCallback = { e: RequestException -> },
    crossinline onFinish: OnUnitCallback = {},
    crossinline onSuccess: OnSuccessCallback<T>
) {

    observe(owner, object : IStateObserver<T> {
        override fun onStart() {
            if (isShowLoading) {
                BaseApp.eventViewModel.showDialog.call()
            }
            onStart()
        }

        override fun onSuccess(data: T) {
            onSuccess(data)
        }

        override fun onEmpty() {
            onEmpty()
        }

        override fun onFailure(e: RequestException) {
            onFailure(e)
        }

        override fun onFinish() {
            if (isShowLoading) {
                BaseApp.eventViewModel.dismissLoading()
            }
            onFinish()
        }
    })
}


fun <T> MutableLiveData<BaseResponse<T>>.request(
    viewModel: ViewModel,
    context: CoroutineContext = Dispatchers.Main,
    request: suspend () -> BaseResponse<T>
) {
    viewModel.viewModelScope.launch(context) {
        this@request.postValue(StartResponse())
        this@request.postValue(request())
    }
}