package com.tuyrt.architecture.capacity.network.observer

import androidx.lifecycle.Observer
import com.tuyrt.architecture.capacity.network.error.RequestException
import com.tuyrt.architecture.capacity.network.response.*

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明： 自定义 Observer，监听 LiveData 的值的变化
 *  配合 协程请求使用
 */
interface IStateObserver<T> : Observer<BaseResponse<T>> {

    override fun onChanged(response: BaseResponse<T>?) {
        if (response is StartResponse) {
            onStart() // 协程开始时，监听到 start事件，不能再执行onFinish()
            return
        }
        when (response) {
            is SuccessResponse -> onSuccess(response.data)
            is EmptyResponse -> onEmpty()
            is FailureResponse -> onFailure(response.exception)
        }
        onFinish()
    }


    /**
     * 请求开始
     */
    fun onStart()

    /**
     * 请求成功，且 data 不为 null
     */
    fun onSuccess(data: T)

    /**
     * 请求成功，但 data 为 null 或者 data 是集合类型但为空
     */
    fun onEmpty()

    /**
     * 请求失败
     */
    fun onFailure(e: RequestException)

    /**
     * 请求结束
     */
    fun onFinish()
}