package com.tuyrt.architecture.capacity.bus

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * 事件总线分发:
 */
// 使用：
// // 普通事件分发
//HiDataBus.with<String>("tag").postValue("result")
//HiDataBus.with<String>("tag").observe(this) {}
// //粘性事件分发
//HiDataBus.with<String>("tag").postStickyData("result")
//HiDataBus.with<String>("tag").observeSticky(this,true) {}

object HiDataBus {

    private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    fun <T> with(eventName: String): StickyLiveData<T> {
        // 基于时间名称  订阅、分发消息
        // 由于 一个livedata 只能发送一种数据类型
        // 所以不同的event事件，需要使用不同的livedata 实例 去分发
        var liveData = eventMap[eventName]
        if (liveData == null) {
            liveData = StickyLiveData<T>(eventName)
            eventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {

        internal var mVersion = 0
        internal var mStickyData: T? = null

        public override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        public override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        fun setStickyData(stickyData: T) {
            mStickyData = stickyData
            setValue(stickyData)
        }

        fun postStickyData(stickyData: T) {
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observeSticky(owner, false, observer)
        }

        fun observeSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            // 允许指定注册的观察者 是否需要关心粘性事件
            // sticky=true , 如果之前已经存在已经发送的数据，那么这个observer会收到之前的粘性事件消息
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                //监听 宿主 发生销毁事件，主动把livedata 移除掉。
                if (event == Lifecycle.Event.ON_DESTROY) {
                    eventMap.remove(eventName)
                }
            })
            super.observe(owner, StickyObserver(this, sticky, observer))
        }


    }

    class StickyObserver<T>(
        private val stickyLiveData: StickyLiveData<T>,
        private val sticky: Boolean,
        private val observer: Observer<in T>,
    ) : Observer<T> {

        //lastVersion 和livedata的version 对齐的原因，就是为控制黏性事件的分发。
        //sticky 不等于true , 只能接收到注册之后发送的消息，如果要接收黏性事件，则sticky需要传递为true
        private var mLastVersion = stickyLiveData.mVersion

        override fun onChanged(t: T) {
            if (mLastVersion >= stickyLiveData.mVersion) {
                //就说明stickyLiveData  没有更新的数据需要发送。
                if (sticky && stickyLiveData.mStickyData != null) {
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return
            }

            mLastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }
    }
}