package com.yds.liblog.logcatch

import android.util.Log
import android.util.Printer
import java.lang.StringBuilder

class BlockLogPrinter : Printer {

    private var startWorkTimeMillis = 0L

    companion object {
        const val START = ">>>>> Dispatching to"
        const val END = "<<<<< Finished to"
    }

    override fun println(x: String?) {
        trackLogByMonitor(x)
    }

    private fun trackLog(x: String?) {
        if (x?.startsWith(START) == true) {
            startWorkTimeMillis = System.currentTimeMillis()
        } else if (x?.startsWith(END) == true) {
            val duration = System.currentTimeMillis() - startWorkTimeMillis
            if (duration > 4500) {
                Log.e("BlockLogPrinter", "耗时：$duration")
                val stacktrace = Thread.currentThread().stackTrace
                val sb = StringBuilder()
                stacktrace.forEach {
                    sb.append("\n${it.toString()}")
                }
                Log.e("BlockLogPrinter", "${sb.toString()}")
            }
        }
    }

    private fun trackLogByMonitor(x: String?) {
        if (x?.startsWith(START) == true) {
            LogMonitor.startMonitor()
        }
        if (x?.startsWith(END) == true) {
            LogMonitor.removeMonitor()
        }
    }
}