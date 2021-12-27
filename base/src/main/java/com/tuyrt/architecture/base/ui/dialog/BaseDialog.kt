package com.tuyrt.architecture.base.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import com.tuyrt.architecture.R
import com.tuyrt.architecture.R.style

/**
 * Created by tuyrt7 on 2021/12/17.
 * 说明：dialog的基类
 *
 *  @lifecycle 用于绑定binding宿主的生命周期之后，自动销毁
 */
abstract class BaseDialog @JvmOverloads constructor(
    context: Context,
    @StyleRes themeResId: Int = style.BaseDialogStyle
) : AppCompatDialog(context, themeResId) {

    private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var gravity: Int = Gravity.CENTER
    private var animRes: Int = -1
    private var dimAmount: Float = 0.5f
    private var alpha: Float = 1f
    //是否支持点击关闭
    private var mIsCancelable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initial(savedInstanceState)
        refreshAttributes()
    }

    /**
     *  如果不使用 binding 的方式，在此处设置 contentView
     */
    protected open fun initContentView() {

    }

    /**
     *  初始化内容
     */
    abstract fun initial(savedInstanceState: Bundle?)

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

    protected open fun refreshAttributes() {
        window!!.let {
            val params: WindowManager.LayoutParams = it.attributes
            params.width = width
            params.height = height
            params.gravity = gravity
            params.windowAnimations = animRes
            params.dimAmount = dimAmount
            params.alpha = alpha
            params.windowAnimations = animRes
            it.attributes = params
        }

        setCanceledOnTouchOutside(mIsCancelable)
    }


}