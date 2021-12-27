package com.tuyrt.architecture.ui.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.base.ui.dialog.BaseDialog
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.databinding.DialogLoadingBinding

/**
 * Created by tuyrt7 on 2021/12/27.
 * 说明： 简单的loading
 */
class LoadingDialog(context: Context, lifecycle: Lifecycle) : BaseDialog(context) {

    private val binding: DialogLoadingBinding by viewbind(lifecycle)
    private val tvTips: TextView by lazy { binding.tvLoadingTips }

    override fun initial(savedInstanceState: Bundle?) {
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     *  设置loading文本提示
     */
    fun setLoadingText(loadingText: String?) {
        if (!TextUtils.isEmpty(loadingText)) {
            tvTips.visibility = View.VISIBLE
            tvTips.text = loadingText
        } else {
            tvTips.visibility = View.GONE
        }
    }
}