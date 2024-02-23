package com.yds.liblog.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yds.liblog.db.LoggerModel

@Dao
interface LoggerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoggerLog(loggerModel: LoggerModel?)

    @Query(value = "select * from logger_model order by id desc")
    fun queryLoggerLog(): List<LoggerModel>?

}