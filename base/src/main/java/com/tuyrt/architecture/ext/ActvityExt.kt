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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.jetbrains.annotations.NotNull

/**
 * Created by tuyrt7 on 2021/12/7.
 * 说明：
 */

/**
 *  启动Activity
 */
inline fun <reified T : Activity> Activity.goActivity(bundle: Bundle? = null) {
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

/**
 *  设置toolbar
 */
fun AppCompatActivity.showToolbar(
    @NotNull toolbar: Toolbar,
    titleStr: String? = null,
    showBackArrow: Boolean = true,
    backBlock: (() -> Unit) = { finish() }
) {
    toolbar.run {
        titleStr?.let {
            title = it
        }
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(showBackArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        setNavigationOnClickListener { backBlock() }
    }
}

fun Fragment.showToolbar(
    @NotNull toolbar: Toolbar,
    titleStr: String? = null,
    showBackArrow: Boolean = true,
    backBlock: (() -> Unit) = { requireActivity().finish() }
) {
    val activity = requireActivity() as AppCompatActivity
    toolbar.run {
        titleStr?.let {
            title = it
        }
        toolbar.title = title
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setHomeButtonEnabled(showBackArrow)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        setNavigationOnClickListener { backBlock() }
    }
}