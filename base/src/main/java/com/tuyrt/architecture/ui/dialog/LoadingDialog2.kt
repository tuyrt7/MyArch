package com.tuyrt.architecture.ui.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.R
import com.tuyrt.architecture.base.ui.dialog.BaseDialogFragment
import com.tuyrt.architecture.databinding.DialogLoadingBinding

/**
 * Created by tuyrt7 on 2021/12/3.
 * 说明：加载中对话框
 * 使用：
 *  LoadingDialog2().show(this)
 */
class LoadingDialog2 : BaseDialogFragment(R.layout.dialog_loading) {

    private val screenWidth by lazy {
        val point = Point()
        (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getRealSize(
            point
        )
        point.x
    }

    private val binding: DialogLoadingBinding by viewbind()
    private val tvTips: TextView by lazy { binding.tvLoadingTips }

    override fun initial(view: View, savedInstanceState: Bundle?) {
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun createObserver() {
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
            tvTips.visibility = View.VISIBLE
            tvTips.text = loadingText
        } else {
            tvTips.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = LoadingDialog2::class.java.simpleName
        private fun showDialog(fm: FragmentManager, tips: String? = null) {
            (fm.findFragmentByTag(TAG) as? LoadingDialog2)?.dismiss()
            LoadingDialog2().apply {
                tips?.let { setLoadingText(it) }
                showNow(fm, TAG)
            }
        }

        private fun dismissDialog(fm: FragmentManager) {
            (fm.findFragmentByTag(TAG) as? LoadingDialog2)?.dismiss()
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