package com.tuyrt.architecture.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by tuyrt7 on 2021/12/8.
 * 说明：
 */

fun ViewModel.launchFlow(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch { block() }
}

fun AppCompatActivity.launchLifecycleScope(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch { block() }
}