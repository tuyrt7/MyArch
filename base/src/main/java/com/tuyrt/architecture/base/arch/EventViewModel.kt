package com.tuyrt.architecture.base.arch

import androidx.lifecycle.ViewModel
import com.tuyrt.architecture.ext.SingleLiveEvent
import com.tuyrt.architecture.ext.launchUI
import com.tuyrt.architecture.ext.postEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：全局 ViewModel，用于处理全局消息
 */
class EventViewModel : ViewModel() {

    val loadingEvent by lazy { SingleLiveEvent<Boolean>() }
    val toastEvent by lazy { SingleLiveEvent<String?>() }

    fun showLoading() {
        loadingEvent.postEvent(true)
    }

    fun dismissLoading() {
        loadingEvent.postEvent(false)
    }

    fun toast(msg: String?) {
        toastEvent.postValue(msg)
    }
}