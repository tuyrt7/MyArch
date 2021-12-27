package com.tuyrt.architecture.base.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tuyrt.architecture.R

/**
 * Created by tuyrt7 on 2021/12/24.
 * 说明：DialogFragment的基类
 */
abstract class BaseDialogFragment(@LayoutRes contentLayoutId: Int = 0) : DialogFragment(contentLayoutId) {

    private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var gravity: Int = Gravity.CENTER
    // 弹窗样式
    private var animRes: Int = R.style.BaseDialogStyle
    // 背景透明
    private var dimAmount: Float = 0.4f
    private var alpha: Float = 1f
    //是否支持点击关闭
    private var mIsCancelable = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial(view, savedInstanceState)
        createObserver()
    }



    override fun onStart() {
        // 在super之前执行，因为super.onStart()中dialog会执行自己的show方法
        refreshAttributes()
        super.onStart()
    }

    /**
     * 设置宽度
     */
    fun setWidth(width: Int) {
        this.width = width
    }

    /**
     * 设置高度
     */
    fun setHeight(height: Int) {
        this.height = height
    }

    /**
     * 设置位置
     */
    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    /**
     * 设置窗口透明度
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float) {
        this.dimAmount = dimAmount
    }

    /**
     * 设置背景透明度
     */
    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        this.alpha = alpha
    }

    /**
     * 设置显示和隐藏动画
     */
    fun setAnimationRes(animation: Int) {
        this.animRes = animation
    }

    /**
     * 刷新属性
     */
    protected open fun refreshAttributes() {
        if (dialog != null) {
            dialog!!.window!!.let {
                // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val params: WindowManager.LayoutParams = it.attributes
                params.width = width
                params.height = height
                params.gravity = gravity
                params.dimAmount = dimAmount
                params.alpha = alpha
                params.windowAnimations = animRes
                it.attributes = params
            }
            isCancelable = mIsCancelable
        }
    }

    abstract fun initial(view: View, savedInstanceState: Bundle?)
    abstract fun createObserver()

     fun isShow(): Boolean {
        return dialog != null && dialog!!.isShowing
    }

    fun show(activity: FragmentActivity) {
        show(activity.supportFragmentManager)
    }

    fun show(fragment: Fragment) {
        show(fragment.childFragmentManager)
    }

    fun show(manager: FragmentManager) {
        if (!isShow()) {
            show(manager, this::javaClass.name)
        }
    }

    fun close() {
        if (isShow()) {
            dismiss()
        }
    }

    fun closeAllowingStateLoss() {
        if (isShow()) {
            dismissAllowingStateLoss()
        }
    }


}