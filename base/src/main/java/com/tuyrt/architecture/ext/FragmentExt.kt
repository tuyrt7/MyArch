package com.tuyrt.architecture.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle


/**
 * AndroidX的FragmentTransaction模式的懒加载:
 */

// FrameLayout绑定多个Fragment
fun loadFragments(
    @IdRes containerId: Int,
    showPosition: Int,
    fragmentManager: FragmentManager,
    fragments: List<Fragment>
) {

    if (fragments.isNotEmpty()) {
        fragmentManager.beginTransaction().apply {
            for (index in fragments.indices) {
                val fragment = fragments[index]
                add(containerId, fragment, fragment.javaClass.name)
                // 通过setMaxLifecycle设置最大生命周期
                if (showPosition == index) {
                    setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                } else {
                    hide(fragment)
                    setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
        }.commit()
    } else {
        throw IllegalStateException(
            "fragments must not empty"
        )
    }
}

// 控制Fragment的显示/隐藏
fun showFragment(fragmentManager: FragmentManager, showFragment: Fragment) {
    fragmentManager.beginTransaction().apply {
        show(showFragment)
        // 对可见的Fragment设置最大生命周期
        setMaxLifecycle(showFragment, Lifecycle.State.RESUMED)

        //获取其中所有的fragment,其他的fragment进行隐藏
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != showFragment) {
                hide(fragment)
                setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
        }
    }.commit()
}

/**
 *  注意####
 *  测试得到 Fragment+Fragment 嵌套时，依然会调用不可见Fragment的 onResume 方法，
 *  所以FragmentTransaction模式不能单单只靠 onResume 判断还要加上 isHidden
 */
// Fragment中 onResume：
//override fun onResume() {
//    super.onResume()
//    if (!isLoaded && !isHidden) {
//        lazyInit()
//        isLoaded = true
//    }
//}