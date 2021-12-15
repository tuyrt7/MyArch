package com.tuyrt.architecture.base.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuyrt.architecture.capacity.network.data.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by tuyrt7 on 2021/12/14.
 * 说明：
 */
open class BaseViewModel : ViewModel() {

    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    fun <T> flowCollect(block: suspend () -> Flow<BaseResponse<T>>, result:(BaseResponse<T>)->Unit) {
        launchUI {
            block().collect { result(it) }
        }
    }
}