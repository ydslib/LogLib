package com.yds.liblog.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yds.liblog.db.BlockModel


@Dao
interface BlockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBlockLog(blockModel: BlockModel?)

    @Query("select * from block_model order by id desc")
    fun queryBlockLog(): List<BlockModel>?

    @Query("select * from block_model order by id desc")
    suspend fun queryBlockLogSuspend(): List<BlockModel>?


}