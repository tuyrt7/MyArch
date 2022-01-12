package com.tuyrt.architecture.base.arch.backpress

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*

/**
 * Created by tuyrt7 on 2022/1/12.
 * 说明： Fragment 回退 核心类
 */
class BackPressRegistry {

    private val mBackPressObservers: LinkedList<BackPressObserver> = LinkedList()

    fun registerBackPress(owner: LifecycleOwner, backPressObserver: BackPressObserver) {
        val lifecycle = owner.lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        mBackPressObservers.add(backPressObserver)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    mBackPressObservers.remove(backPressObserver)
                    lifecycle.removeObserver(this)
                }
            }
        })
    }

    fun dispatchBackPress(): Boolean {
        val observerCount = mBackPressObservers.size
        for (i in (observerCount - 1) downTo 0) {
            val backPressObserver = mBackPressObservers[i]
            if (backPressObserver.onBackPress()) return true
        }
        return false
    }
}

