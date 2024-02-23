package com.yds.liblog.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yds.liblog.db.CrashModel

@Dao
interface CrashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrashLog(log: CrashModel?)

    @Query("select * from crash_model order by id desc")
    fun queryCrashLog():List<CrashModel>?

    @Query("select * from crash_model order by id desc")
    suspend fun queryCrashLogSuspend():List<CrashModel>?

}