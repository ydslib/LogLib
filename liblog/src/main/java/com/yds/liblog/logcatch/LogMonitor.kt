package com.yds.liblog.logcatch

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.yds.liblog.SLog

object LogMonitor {

    private val mLogThread by lazy {
        HandlerThread("log")
    }

    private val mIoHandler by lazy {
        Handler(mLogThread.looper)
    }

    init {
        mLogThread.start()
    }

    private val mLogRunnable by lazy {
        Runnable {
            val sb = StringBuilder()
            val stackTrace = Looper.getMainLooper().thread.stackTrace
            stackTrace.forEach {
                Log.e("TAG", it.toString())
                sb.append(it.toString() + "\n")
            }
            SLog.saveBlockLog(sb.toString())
        }
    }

    fun startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, 4500)
    }

    fun removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable)
    }

}