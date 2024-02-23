package com.yds.liblog.factory

import android.content.Context
import android.util.Log
import com.yds.liblog.SLog
import com.yds.liblog.db.LogDatabase
import com.yds.liblog.db.LoggerModel
import java.text.SimpleDateFormat

class Logger : ILog {

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun saveLoggerLog(context: Context?, tag: String, msg: String, level: String) {
        val time = SimpleDateFormat(SLog.TIME_FORMAT).format(System.currentTimeMillis())
        val log = LoggerModel(time = time, level = level, tag = tag, msg = msg)
        if (context != null) {
            saveLog(context.applicationContext, log)
        }
    }

    override fun <T> saveLog(context: Context, log: T?) {
        synchronized(this){
            LogDatabase.getInstance(context).loggerDao().insertLoggerLog(log as? LoggerModel)
        }
    }

    override fun queryLog(context: Context): List<Any>? {
        return LogDatabase.getInstance(context).loggerDao().queryLoggerLog()
    }


}