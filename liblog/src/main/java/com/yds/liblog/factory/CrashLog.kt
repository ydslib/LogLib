package com.yds.liblog.factory

import android.content.Context
import com.yds.liblog.SLog
import com.yds.liblog.db.CrashModel
import com.yds.liblog.db.LogDatabase
import java.text.SimpleDateFormat


class CrashLog : ILog {

    override fun <T> saveLog(context: Context, log: T?) {
        val msg = log as? String
        val time = SimpleDateFormat(SLog.TIME_FORMAT).format(System.currentTimeMillis())
        val crashModel = CrashModel(time = time, msg = msg)
        synchronized(this){
            LogDatabase.getInstance(context).crashDao().insertCrashLog(crashModel)
        }
    }

    override fun queryLog(context: Context): List<Any>? {
        return LogDatabase.getInstance(context).crashDao().queryCrashLog()
    }


}