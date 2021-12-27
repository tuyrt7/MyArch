package com.tuyrt.myarch.test.binding

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.hi.dhl.binding.databind
import com.tuyrt.architecture.base.ui.dialog.BaseDialog
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.DialogExampleBindingBinding

/**
 * Created by tuyrt7 on 2021/12/24.
 * 说明：样例 binding Dialog
 * 使用：
 *    TestBindingDialog(this@TestActivity, lifecycle).show()
 */
class TestBindingDialog(context: Context, lifecycle: Lifecycle) : BaseDialog(context) {

   // 使用 dataBinding
   val binding: DialogExampleBindingBinding by databind(R.layout.dialog_example_binding, lifecycle)
   // 使用 viewBinding
   // val binding2: DialogExampleBindingBinding by viewbind(lifecycle)

   @SuppressLint("SetTextI18n")
   override fun initial(savedInstanceState: Bundle?) {
      binding.apply { result.text = "TestBindingDialog" }
   }
}