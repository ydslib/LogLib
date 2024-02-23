package com.yds.liblog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yds.liblog.db.CrashModel
import com.yds.liblog.db.LogDatabase
import com.yds.liblog.db.LoggerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogViewModel : ViewModel() {

    val loggerLiveData by lazy { MutableLiveData<List<LoggerModel>?>() }
    val crashLiveData by lazy { MutableLiveData<List<CrashModel>?>() }
    val blockLiveData by lazy { MutableLiveData<List<CrashModel>?>() }

    fun queryLoggerLog(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val loggerDao = LogDatabase.getInstance(context).loggerDao()
                loggerLiveData.postValue(loggerDao.queryLoggerLogSuspend())
            }
        }
    }

    fun queryCrashLog(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val crashDao = LogDatabase.getInstance(context).crashDao()
                crashLiveData.postValue(crashDao.queryCrashLogSuspend())
            }
        }
    }

    fun queryBlockLog(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val blockDao = LogDatabase.getInstance(context).blockDao()
                val dataList = mutableListOf<CrashModel>()
                blockDao.queryBlockLogSuspend()?.forEach { model ->
                    dataList.add(CrashModel(time = model.time, msg = model.msg))
                }
                blockLiveData.postValue(dataList.toList())
            }
        }
    }

}