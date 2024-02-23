package com.yds.liblog.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RenameTable
import java.io.Serializable


@Entity(tableName = "logger_model")
data class LoggerModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time: String? = null,
    var level: String? = null,
    var tag: String? = null,
    var msg: String? = null
):Serializable {
    override fun toString(): String {
        return "时间：$time\n" +
                "日志级别：$level\n" +
                "日志TAG：$tag\n" +
                "日志信息：$msg"
    }
}


@Entity(tableName = "crash_model")
data class CrashModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time:String?=null,
    var msg:String?=null
):Serializable{
    override fun toString(): String {
        return "时间：$time\n" +
                "日志信息：$msg"
    }
}

@Entity(tableName = "block_model")
data class BlockModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time:String?=null,
    var msg:String?=null
):Serializable{
    override fun toString(): String {
        return "时间：$time\n" +
                "日志信息：$msg"
    }
}