package com.tuyrt.architecture.base.arch

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.ext.isActivityDestroy
import com.tuyrt.architecture.ui.dialog.LoadingDialog

/**
 * Created by tuyrt7 on 2021/12/13.
 * 说明：
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) , ILoadingView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial(savedInstanceState)
        createObserver()
    }

    abstract fun initial(savedInstanceState: Bundle?)

    abstract fun createObserver()

    // -----懒加载-------------------------------------------------

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            KLog.d( "lazyInit:!!!!!!!")
            isLoaded = true
        }
    }

    abstract fun lazyInit()

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
        dismissLoading()
    }

    // -----懒加载-------------------------------------------------end

    override fun showLoading(text: String?) {
        if (!context.isActivityDestroy()) {
            LoadingDialog.show(this, text)
        }
    }

    override fun dismissLoading() {
        LoadingDialog.dismiss(this)
    }
}