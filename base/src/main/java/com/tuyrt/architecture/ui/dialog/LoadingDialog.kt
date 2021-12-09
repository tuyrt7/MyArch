package com.tuyrt.architecture.ui.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tuyrt.architecture.R
import com.tuyrt.architecture.base.ui.dialog.BaseDialogFragment

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：加载中对话框
 */
class LoadingDialog : BaseDialogFragment() {

    private val screenWidth by lazy {
        val point = Point()
        (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getRealSize(
            point
        )
        point.x
    }

    private var tvTips: TextView? = null

    override fun getDialogStyle(): Int? = null

    override fun getDialogTheme(): Int? = null

    override fun getLayoutId() = R.layout.dialog_loading

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        tvTips = view.findViewById(R.id.tv_loading_tips)

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    /* // 设置 DialogFragment 的参数
     override fun onResume() {
         super.onResume()
         dialog?.window?.apply {
             setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
             val dialogSize = (screenWidth * 0.25).toInt()
             setLayout(dialogSize, dialogSize)
         }
     }*/

    // ** call after show
    fun setLoadingText(loadingText: String?) {
        if (!TextUtils.isEmpty(loadingText)) {
            tvTips?.visibility = View.VISIBLE
            tvTips?.text = loadingText
        } else {
            tvTips?.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = LoadingDialog::class.java.simpleName
        private fun showDialog(fm: FragmentManager, tips: String? = null) {
            (fm.findFragmentByTag(TAG) as? LoadingDialog)?.dismiss()
            LoadingDialog().apply {
                tips?.let { setLoadingText(it) }
                showNow(fm, TAG)
            }
        }

        private fun dismissDialog(fm: FragmentManager) {
            (fm.findFragmentByTag(TAG) as? LoadingDialog)?.dismiss()
        }

        fun show(activity: FragmentActivity, tips: String? = null) {
            showDialog(activity.supportFragmentManager, tips)
        }

        fun show(fragment: Fragment, tips: String? = null) {
            showDialog(fragment.childFragmentManager, tips)
        }

        fun dismiss(activity: FragmentActivity) {
            dismissDialog(activity.supportFragmentManager)
        }

        fun dismiss(fragment: Fragment) {
            dismissDialog(fragment.childFragmentManager)
        }
    }
}