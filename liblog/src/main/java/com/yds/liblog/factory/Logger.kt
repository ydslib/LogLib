package com.yds.liblog.factory

import android.content.Context
import android.util.Log
import com.yds.liblog.SLog
import com.yds.liblog.db.LogDatabase
import com.yds.liblog.db.LoggerModel
import java.text.SimpleDateFormat

class Logger : ILog {

    companion object {
        private const val I = "i"
        private const val V = "v"
        private const val D = "d"
        private const val W = "w"
        private const val E = "e"
    }

    fun i(tag: String, msg: String) {
        printWholeLog(tag, msg, I)
    }

    fun v(tag: String, msg: String) {
        printWholeLog(tag, msg, V)
    }

    fun d(tag: String, msg: String) {
        printWholeLog(tag, msg, D)
    }

    fun w(tag: String, msg: String) {
        printWholeLog(tag, msg, W)
    }

    fun e(tag: String, msg: String) {
        printWholeLog(tag, msg, E)
    }

    private fun printWholeLog(tag: String?, msg: String?, level: String) {
        var modifyMsg = msg ?: ""
        if (tag.isNullOrEmpty()) return
        val segmentSize = 3 * 1024 + 512
        val length = modifyMsg.length.toLong()
        if (length <= segmentSize) { // 长度小于等于限制直接打印
            printLogByLevel(level, tag, modifyMsg)
        } else {
            while (modifyMsg.length > segmentSize) { // 循环分段打印日志
                //截取要打印的部分
                val logContent = modifyMsg.substring(0, segmentSize)
                //截取后面的部分，前面的部分丢弃
                modifyMsg = modifyMsg.substring(segmentSize)
                printLogByLevel(level, tag, logContent)
            }
            printLogByLevel(level, tag, modifyMsg) // 打印剩余日志
        }
    }

    private fun printLogByLevel(level: String, tag: String, msg: String) {
        when (level) {
            I -> {
                Log.i(tag, msg)
            }

            V -> {
                Log.v(tag, msg)
            }

            D -> {
                Log.d(tag, msg)
            }

            W -> {
                Log.w(tag, msg)
            }

            E -> {
                Log.e(tag, msg)
            }
        }
    }

    fun saveLoggerLog(context: Context?, tag: String, msg: String, level: String) {
        val time = SimpleDateFormat(SLog.TIME_FORMAT).format(System.currentTimeMillis())
        val log = LoggerModel(time = time, level = level, tag = tag, msg = msg)
        if (context != null) {
            saveLog(context.applicationContext, log)
        }
    }

    override fun <T> saveLog(context: Context, log: T?) {
        val loggerModel = log as? LoggerModel ?: return
        synchronized(this) {
            LogDatabase.getInstance(context).loggerDao().insertLoggerLog(loggerModel)
        }
    }

    override fun queryLog(context: Context): List<Any>? {
        return LogDatabase.getInstance(context).loggerDao().queryLoggerLog()
    }


}