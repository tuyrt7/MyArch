package com.tuyrt.architecture.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager

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