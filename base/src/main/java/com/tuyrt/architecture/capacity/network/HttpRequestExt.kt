package com.tuyrt.architecture.capacity.network

import com.tuyrt.architecture.base.arch.BaseApp
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.capacity.network.response.*
import com.tuyrt.architecture.capacity.network.error.RequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明： 扩展Flow 状态观察
 */

/**
 * 监听 Flow 的值的变化，回调为 DSL 的形式
 */
suspend inline fun <T> Flow<BaseResponse<T>>.observeWater(
    isShowLoading: Boolean = true,
    isShowErrorToast: Boolean = true,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    this.collect { response ->
        when (response) {
            is StartResponse -> {
                KLog.dt("Flow", "请求开始")
                if (isShowLoading) {
                    BaseApp.eventViewModel.showLoading()
                }
                requestCallback.startCallback?.invoke()
            }
            is SuccessResponse -> {
                KLog.dt("Flow", "请求成功")
                requestCallback.successCallback?.invoke(response.data)
            }
            is EmptyResponse -> requestCallback.emptyCallback?.invoke()
            is FailureResponse -> {
                KLog.dt("Flow", "请求失败 ${response.exception.errorMsg}")
                if (isShowErrorToast) {
                    BaseApp.eventViewModel.toast(response.exception.errorMsg)
                }
                requestCallback.failureCallback?.invoke(response.exception)
            }
            is CompletionResponse -> {
                KLog.dt("Flow", "请求结束")
                if (isShowLoading) {
                    BaseApp.eventViewModel.dismissLoading()
                }
                requestCallback.finishCallback?.invoke()
            }
        }
    }
}

/**
 * 监听 Flow 的值的变化
 */
suspend inline fun <T> Flow<BaseResponse<T>>.observeWaterResponse(
    isShowLoading: Boolean = true,
    isShowErrorToast: Boolean = true,
    crossinline onStart: OnUnitCallback = {},
    crossinline onEmpty: OnUnitCallback = {},
    crossinline onFailure: OnFailureCallback = { e: RequestException -> },
    crossinline onFinish: OnUnitCallback = {},
    crossinline onSuccess: OnSuccessCallback<T>
) {

    collect { response ->
        when (response) {
            is StartResponse -> {
                KLog.dt("Flow", "请求开始")
                if (isShowLoading) {
                    BaseApp.eventViewModel.showLoading()
                }
                onStart()
            }
            is SuccessResponse -> {
                KLog.dt("Flow", "请求成功")
                onSuccess(response.data)
            }
            is EmptyResponse -> onEmpty()
            is FailureResponse -> {
                KLog.dt("Flow", "请求失败")
                if (isShowErrorToast) {
                    BaseApp.eventViewModel.toast(response.exception.errorMsg)
                }
                onFailure(response.exception)
            }
            is CompletionResponse -> {
                KLog.dt("Flow", "请求结束")
                if (isShowLoading) {
                    BaseApp.eventViewModel.dismissLoading()
                }
                onFinish()
            }
        }
    }
}
