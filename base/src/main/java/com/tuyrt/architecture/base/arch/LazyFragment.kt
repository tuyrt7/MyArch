package com.tuyrt.architecture.base.arch

import androidx.fragment.app.Fragment
import com.tuyrt.architecture.capacity.log.KLog

/**
 * Created by tuyrt7 on 2021/12/1.
 * 说明： Androidx 懒加载
 */
abstract class LazyFragment : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            KLog.d( "lazyInit:!!!!!!!")
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}