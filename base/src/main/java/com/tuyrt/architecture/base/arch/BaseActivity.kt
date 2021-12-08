package com.tuyrt.architecture.base.arch

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tuyrt.architecture.ext.launchLifecycleScope
import com.tuyrt.architecture.ext.toast
import com.tuyrt.architecture.ui.dialog.LoadingDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.collect
/**
 * Created by tuyrt7 on 2021/12/1.
 * 说明：
 */
abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId), ILoadingView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial(savedInstanceState)
        initEventObserver()
        initObserver()
    }

    abstract fun initial(savedInstanceState: Bundle?)

    abstract fun initObserver()

    open fun initEventObserver() {
        BaseApp.eventViewModel.showDialog.observe(this) {
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
        }

        launchLifecycleScope {
            BaseApp.eventViewModel.dismissDialogFlow.collect {
                dismissLoading()
            }
        }

        launchLifecycleScope {
            BaseApp.eventViewModel.toastFlow.collectLatest { msg->
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


