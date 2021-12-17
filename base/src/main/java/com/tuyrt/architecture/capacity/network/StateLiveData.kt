package com.tuyrt.architecture.capacity.network

import androidx.lifecycle.*
import com.tuyrt.architecture.base.arch.BaseApp
import com.tuyrt.architecture.capacity.network.*
import com.tuyrt.architecture.capacity.network.response.*
import com.tuyrt.architecture.capacity.network.error.RequestException
import com.tuyrt.architecture.capacity.network.observer.IStateObserver
import com.tuyrt.architecture.capacity.network.observer.IStateObserver2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by tuyrt7 on 2021/12/15.
 * 说明：
 */

// 配合 fire
fun <T> StateLiveData<T>.request(
    viewModel: ViewModel,
    context: CoroutineContext = Dispatchers.Main,
    requestBlock: suspend () -> BaseResponse<T>
) {
    viewModel.viewModelScope.launch(context) {
        this@request.postValue(StartResponse())
        this@request.postValue(requestBlock())
    }
}

class StateLiveData<T> : MutableLiveData<BaseResponse<T>>() {

    /**
     *  liveData 使用此监听观察状态 ：配合 普通协程请求 BaseRepository#fire
     */
     fun observeFire(
        owner: LifecycleOwner,
        isShowLoading: Boolean = true,
        isShowErrorToast: Boolean = true,
         callback: HttpRequestCallback<T>.() -> Unit
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

    /**
     *  liveData 使用此监听观察状态 ：配合Flow请求 BaseRepository#water
     */
    fun observeWater(
        owner: LifecycleOwner,
        isShowLoading: Boolean = true,
        isShowErrorToast: Boolean = true,
        callback: HttpRequestCallback<T>.() -> Unit
    ) {
        val requestCallback = HttpRequestCallback<T>().apply(callback)
        // *** 这里使用 IStateObserver2
        observe(owner, object : IStateObserver2<T> {
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


    inline fun <T> LiveData<BaseResponse<T>>.observeResponse2(
        owner: LifecycleOwner,
        isShowLoading: Boolean = true,
        crossinline onStart: OnUnitCallback = {},
        crossinline onEmpty: OnUnitCallback = {},
        crossinline onFailure: OnFailureCallback = { e: RequestException -> },
        crossinline onFinish: OnUnitCallback = {},
        crossinline onSuccess: OnSuccessCallback<T>
    ) {

        observe(owner, object : IStateObserver2<T> {
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
}