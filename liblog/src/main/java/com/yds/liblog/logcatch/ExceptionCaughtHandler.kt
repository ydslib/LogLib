package com.yds.liblog.logcatch

import android.content.Context
import android.os.Build
import com.yds.liblog.SLog
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.StringBuilder
import java.util.Date

class ExceptionCaughtHandler(
    private val context: Context,
    private val defaultHandler: Thread.UncaughtExceptionHandler? = null
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        val exceptionContent = getCollectExceptionInfo(e)
        SLog.saveCrashLog(exceptionContent.toString())
        defaultHandler?.uncaughtException(t, e)
    }

    private fun getCollectExceptionInfo(e: Throwable): StringBuilder {
        val sb = StringBuilder()
        appendDeviceInfo(sb, e)
        appendThrowableInfo(e, sb)
        return sb
    }

    private fun appendDeviceInfo(builder: StringBuilder, e: Throwable) {
        builder.append("OS Version(设备系统): ${Build.VERSION.RELEASE}\n")
        builder.append("Device Vendor(设备制造商): ${Build.MANUFACTURER}\n")
        // Crash地点
        val stackTraces = e.stackTrace
        if (!stackTraces.isNullOrEmpty()) {
            val firstTrace = stackTraces[0]
            builder.append("Location(发生位置): ${firstTrace.className}: line-${firstTrace.lineNumber}\n")
        }
        // Crash的主要信息
        builder.append("Key Message(关键信息): ${e.message}\n")
    }

    // 保存抛出异常后的栈信息
    private fun appendThrowableInfo(e: Throwable, stringBuilder: StringBuilder) {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        //获取到堆栈信息
        e.printStackTrace(printWriter)
        var cause = e.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        // 转换异常信息
        val errorStackInfo = stringWriter.toString()
        stringBuilder.append("All Details(全部详情):\n")
        stringBuilder.append(errorStackInfo)
    }

    companion object {
        fun initCrashLog(context: Context) {
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            val caughtHandler = ExceptionCaughtHandler(context, defaultHandler)
            Thread.setDefaultUncaughtExceptionHandler(caughtHandler)
        }
    }

}