package com.tuyrt.architecture.ext

import android.content.Context
import android.widget.Toast
import com.tuyrt.architecture.base.arch.BaseApp

/**
 * Created by tuyrt7 on 2021/12/6.
 * 说明：通用扩展方法
 */

fun toast(msg: String?) {
    msg?.takeIf { it.isNotEmpty() }?.also {
        Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_SHORT).show()
    }
}

/**
 * 获取屏幕宽度
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

/**
 * dp值转换为px
 */
fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * px值转换成dp
 */
fun Context.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * 判断是否为空 并传入相关操作
 *  非空-> 执行第 1个lambda
 *  空  -> 执行第 2个lambda
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}

/**
 * 判断是否为空 并传入相关操作
 */
/*fun <T> Any?.notNull(f: () -> T, t: () -> T): T {
    return if (this != null) f() else t()
}*/

/**
 *  类型转换
 */
fun <T> Any.safeAs(): T? {
    return this as? T
}

