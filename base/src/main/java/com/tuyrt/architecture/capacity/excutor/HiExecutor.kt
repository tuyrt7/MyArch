package com.tuyrt.lib.executor

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.annotation.MainThread
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by tuyrt7 on 2021/6/29.
 * 说明： 常用线程管理
 */
object HiExecutor {
    private const val TAG = "HiExecutor"

    private var hiExecutor: ThreadPoolExecutor
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private var workHandler: Handler? = null

    init {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockQueue = ArrayBlockingQueue<Runnable>(128)

        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            // hi-executor-0
            thread.name = "hi-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }

        hiExecutor = ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime, unit,
            blockQueue,
            threadFactory,
            RejectedExecutionHandler { _, _ ->
                Log.d(TAG, "HiExecutor ----RejectedExecutionHandler----")
            }
        )
    }

    fun execute(runnable: Runnable) {
        hiExecutor.execute(runnable)
    }

    // HandlerThread 异步任务
    fun runOnHandlerThread(runnable: Runnable): Boolean {
        ensureSubHandler()
        return workHandler!!.post(runnable)
    }

    // HandlerThread 异步延时任务
    fun runDelayedOnHandlerThread(runnable: Runnable, delayMillis: Long): Boolean {
        ensureSubHandler()
        return workHandler!!.postDelayed(runnable, delayMillis)
    }

    // HandlerThread 异步定时任务
    fun runAtTimeOnHandlerThread(runnable: Runnable, uptimeMillis: Long): Boolean {
        ensureSubHandler()
        return workHandler!!.postAtTime(runnable, uptimeMillis)
    }

    // 主线程任务
    fun runOnUIThread(runnable: Runnable) {
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

    // 发送消息到消息队列最前面 （场景：发送紧急消息）
    fun sendAtFrontOfQueue(runnable: Runnable) {
        val msg = Message.obtain(mainHandler, runnable)
        mainHandler.sendMessageAtFrontOfQueue(msg)
    }

    // 主线程延时任务
    fun runDelayedOnUIThread(runnable: Runnable, delayMillis: Long): Boolean {
        return mainHandler.postDelayed(runnable, delayMillis)
    }

    // 主线程定时任务
    fun runAtTimeOnUIThread(runnable: Runnable, uptimeMillis: Long): Boolean {
        return mainHandler.postAtTime(runnable, uptimeMillis)
    }

    // 移除主线程的任务
    fun removeUICallbacks(runnable: Runnable) {
        mainHandler.removeCallbacks(runnable)
    }

    // 移除异步线程的任务
    fun removeHandlerThreadCallbacks(runnable: Runnable) {
        workHandler?.removeCallbacks(runnable)
    }

    // 结束线程池
    fun shutdown() {
        if (!hiExecutor.isShutdown) {
            hiExecutor.shutdown()
        }
        if (workHandler != null) {
            workHandler!!.looper.quitSafely()
            workHandler!!.looper.thread.interrupt()
        }
    }

    // 线程池异步任务，控制过程、返回值，仿 AsyncTask，
    // 使用：HiExecutor.execute(object:Callable<String>(){...})
    abstract class Callable<T> : Runnable {
        override fun run() {
            val prepareRunnable = Runnable {
                onPrepare()
            }
            mainHandler.post(prepareRunnable)

            val t: T? = onBackground()

            // 移除 prepareRunnable 任务，防止需要执行onCompleted了，onPrepare还未执行，那就不需要执行了
            mainHandler.removeCallbacks(prepareRunnable)
            mainHandler.post { onCompleted(t) }
        }

        // 回调在主线程
        open fun onPrepare() {
            // 转菊花
        }
        abstract fun onBackground(): T?
        abstract fun onCompleted(result: T?)
    }

    @Synchronized
    private fun ensureSubHandler() {
        if (workHandler == null) {
            val handlerThread = HandlerThread("WorkHandler")
            handlerThread.start()
            workHandler = Handler(handlerThread.looper)
        }
    }

}