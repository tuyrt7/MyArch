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

    val showDialog by lazy { SingleLiveEvent<String?>() }
    val dismissDialog by lazy { SingleLiveEvent<Void>() }
    val toastEvent by lazy { SingleLiveEvent<String?>() }

    fun showLoading(msg: String?) {
        showDialog.postEvent(msg)
    }

    fun dismissLoading() {
        dismissDialog.postEvent(null)
    }

    fun toast(msg: String?) {
        toastEvent.postValue(msg)
    }

    // Flow
    val showDialogFlow = MutableSharedFlow<String?>()
    val dismissDialogFlow = MutableSharedFlow<Void?>()
    val toastFlow = MutableSharedFlow<String?>()

    fun showLoadingFlow(msg: String?) {
        launchUI {
            showDialogFlow.emit(msg)
        }
    }

    fun dismissLoadingFlow() {
        launchUI {
            dismissDialogFlow.emit(null)
        }
    }

    fun toastFlow(msg: String?) {
        launchUI {
            toastFlow.emit(msg)
        }
    }
}