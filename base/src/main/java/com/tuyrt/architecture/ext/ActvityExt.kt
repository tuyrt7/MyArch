package com.tuyrt.architecture.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by tuyrt7 on 2021/12/7.
 * 说明：
 */

/**
 *  启动Activity
 */
inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

/**
 * 点击外部隐藏软键盘，提升用户体验
 */
fun Activity.initSoftKeyboard() {
    findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).setOnClickListener { hideSoftKeyboard() }
}

/**
 * 隐藏软键盘
 */
fun Activity.hideSoftKeyboard() {
    val view = currentFocus
    view?.let {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Context?.isActivityDestroy(): Boolean {
    val activity = this?.let { findActivity(it) }
    if (activity != null) {
        return activity.isDestroyed || activity.isFinishing
    }
    return true
}

private fun findActivity(context: Context): Activity? {
    return when (context) {
        is Activity -> context
        is ContextWrapper -> findActivity(context.baseContext)
        else -> null
    }
}

