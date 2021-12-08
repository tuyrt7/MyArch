package com.tuyrt.architecture.capacity.network

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：网络请求回调
 */

typealias OnSuccessCallback<T> = (data: T) -> Unit
typealias OnFailureCallback = (e: RequestException) -> Unit
typealias OnUnitCallback = () -> Unit

class HttpRequestCallback<T> {

    var startCallback: OnUnitCallback? = null
    var successCallback: OnSuccessCallback<T>? = null
    var emptyCallback: OnUnitCallback? = null
    var failureCallback: OnFailureCallback? = null
    var finishCallback: OnUnitCallback? = null

    fun onStart(block: OnUnitCallback) {
        startCallback = block
    }

    fun onSuccess(block: OnSuccessCallback<T>) {
        successCallback = block
    }

    fun onEmpty(block: OnUnitCallback) {
        emptyCallback = block
    }

    fun onFailure(block: OnFailureCallback) {
        failureCallback = block
    }

    fun onFinish(block: OnUnitCallback) {
        finishCallback = block
    }

}