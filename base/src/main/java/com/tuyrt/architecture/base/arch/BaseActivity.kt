package com.tuyrt.architecture.base.arch

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tuyrt.architecture.anno.ActivityConfiguration
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.ext.launchLifecycleScope
import com.tuyrt.architecture.ext.toast
import com.tuyrt.architecture.ui.dialog.LoadingDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by tuyrt7 on 2021/12/1.
 * 说明：基类 BaseActivity
 *  viewModel的创建可以使用官方的扩展函数，所以未做封装
 *  viewBinding 和 dataBinding 的创建，使用 hi-dhl 的 binding库，使用kotlin委托函数，需要的时候才创建使用
 */
abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0) : AppCompatActivity(contentLayoutId), ILoadingView {

    private var needLogin = false

    init {
        this.javaClass.getAnnotation(ActivityConfiguration::class.java)?.let {
            needLogin = it.needLogin
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial(savedInstanceState)
        initEventObserver()
        createObserver()
    }

    abstract fun initial(savedInstanceState: Bundle?)

    abstract fun createObserver()

    open fun initEventObserver() {
        BaseApp.eventViewModel.showDialog.observe(this) {
            KLog.d("${this.javaClass.simpleName} eventViewModel showDialog ")
            showLoading(it)
        }

        BaseApp.eventViewModel.dismissDialog.observe(this) {
            dismissLoading()
        }

        BaseApp.eventViewModel.toastEvent.observe(this) { msg ->
            toast(msg)
        }

        // Flow
        launchLifecycleScope {
            BaseApp.eventViewModel.showDialogFlow.collect {
                showLoading(it)
            }
            BaseApp.eventViewModel.dismissDialogFlow.collect {
                dismissLoading()
            }

            BaseApp.eventViewModel.toastFlow.collectLatest { msg ->
                toast(msg)
            }
        }
    }

    override fun showLoading(text: String?) {
        if (!isFinishing) {
            LoadingDialog.show(this, text)
        }
    }

    override fun dismissLoading() {
        LoadingDialog.dismiss(this)
    }

    override fun onDestroy() {
        dismissLoading()
        super.onDestroy()
    }
}


