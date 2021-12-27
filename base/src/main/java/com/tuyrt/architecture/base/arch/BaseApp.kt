package com.tuyrt.architecture.base.arch

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.tuyrt.architecture.capacity.log.KLog

/**
 * Created by tuyrt7 on 2021/12/6.
 * 说明：
 */
open class BaseApp : Application(), ViewModelStoreOwner {

    private val appViewModelProvider by lazy {
        ViewModelProvider(this, AndroidViewModelFactory.getInstance(this))
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        eventViewModel = appViewModelProvider[EventViewModel::class.java]

        KLog.init(true, "arch--")
    }

    override fun getViewModelStore(): ViewModelStore = ViewModelStore()

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context

        // 全局 viewModel
        lateinit var eventViewModel: EventViewModel
    }
}