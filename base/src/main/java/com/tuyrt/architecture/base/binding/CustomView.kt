package com.tuyrt.architecture.base.binding

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.hi.dhl.binding.databind
import com.hi.dhl.binding.viewbind
import com.tuyrt.architecture.R
import com.tuyrt.architecture.databinding.LayoutExampleCustomViewBinding

/**
 * Created by tuyrt7 on 2021/12/24.
 * 说明：样例 binding 自定义ViewGroup
 */
class CustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attr, defStyleAttr) {

    // dataBinding 用法
    val binding: LayoutExampleCustomViewBinding by databind(R.layout.layout_example_custom_view)

    // ViewBinding 两种用法:
    //1. 当根布局是非 merge 标签，使用此方法进行初始化
    val binding2: LayoutExampleCustomViewBinding by viewbind()
    //2. 当根布局为 merge 标签，使用此方法进行初始化
    //val binding3: LayoutExampleCustomViewBinding by viewbind(this)

    init {
        with(binding) {
            result.text = "这是 ViewGroup 通过 DataBinding 绑定"
        }
    }
}