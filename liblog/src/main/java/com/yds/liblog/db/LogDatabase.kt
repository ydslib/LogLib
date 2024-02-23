package com.yds.liblog.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yds.liblog.db.dao.BlockDao
import com.yds.liblog.db.dao.CrashDao
import com.yds.liblog.db.dao.LoggerDao


@Database(entities = [LoggerModel::class, CrashModel::class, BlockModel::class], version = 1)
abstract class LogDatabase : RoomDatabase() {

    abstract fun loggerDao(): LoggerDao
    abstract fun crashDao(): CrashDao
    abstract fun blockDao(): BlockDao

    companion object {
        const val DATABASE_NAME = "app_log"
        private var instance: LogDatabase? = null
        fun getInstance(context: Context): LogDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): LogDatabase {
            return Room.databaseBuilder(context, LogDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}