package com.yds.liblog.factory

import android.content.Context
import com.yds.liblog.SLog
import com.yds.liblog.db.BlockModel
import com.yds.liblog.db.LogDatabase
import java.text.SimpleDateFormat

class BlockLog : ILog {

    override fun <T> saveLog(context: Context, log: T?) {
        val msg = log as? String
        val time = SimpleDateFormat(SLog.TIME_FORMAT).format(System.currentTimeMillis())
        val blockModel = BlockModel(time = time, msg = msg)
        synchronized(this){
            LogDatabase.getInstance(context).blockDao().insertBlockLog(blockModel)
        }
    }

    override fun queryLog(context: Context): List<Any>? {
        return LogDatabase.getInstance(context).blockDao().queryBlockLog()
    }


}