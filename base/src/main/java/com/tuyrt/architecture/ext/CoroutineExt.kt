package com.tuyrt.architecture.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.tuyrt.architecture.capacity.network.data.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by tuyrt7 on 2021/12/8.
 * 说明：
 */

fun ViewModel.launchUI(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch { block() }
}

fun <T> ViewModel.launchFlowAndCollect(request: suspend () -> Flow<BaseResponse<T>>, result: (BaseResponse<T>) -> Unit) {
    launchUI {
        request().collect { result(it) }
    }
}

fun AppCompatActivity.launchLifecycleScope(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch { block() }
}

