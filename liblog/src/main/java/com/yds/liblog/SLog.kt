package com.yds.liblog

import android.content.Context
import android.os.Looper
import com.yds.liblog.factory.LogFactoryManager
import com.yds.liblog.factory.Logger
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

object SLog {

    //logger日志的类型
    const val LOG = "log"

    //异常日志的类型
    const val CRASH_LOG = "crash_log"

    //卡顿日志的类型
    const val BLOCK_LOG = "block_log"

    //时间格式
    const val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    //context的弱引用
    var contextRef: WeakReference<Context>? = null

    //存储日志到数据库的线程池
    private val logTaskExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }

    //初始化
    fun initLog(context: Context) {
        contextRef = WeakReference(context)
    }

    val logger by lazy {
        LogFactoryManager.getLogInstance(LOG) as Logger
    }

    /**
     * 日志打印以及保存
     * saveLog：是否存储日志
     */
    fun i(tag: String, msg: String, saveLog: Boolean = false) {
        if (saveLog) {
            saveLogger(contextRef?.get(), tag, msg, "i")
        }
        logger.i(tag, msg)
    }

    fun v(tag: String, msg: String, saveLog: Boolean = false) {
        if (saveLog) {
            saveLogger(contextRef?.get(), tag, msg, "v")
        }
        logger.v(tag, msg)
    }

    fun d(tag: String, msg: String, saveLog: Boolean = false) {
        if (saveLog) {
            saveLogger(contextRef?.get(), tag, msg, "d")
        }
        logger.d(tag, msg)
    }

    fun w(tag: String, msg: String, saveLog: Boolean = false) {
        if (saveLog) {
            saveLogger(contextRef?.get(), tag, msg, "w")
        }
        logger.w(tag, msg)
    }

    fun e(tag: String, msg: String, saveLog: Boolean = false) {
        if (saveLog) {
            saveLogger(contextRef?.get(), tag, msg, "e")
        }
        logger.e(tag, msg)
    }

    /**
     * 保存日志
     * type:日志类型 LOG,CRASH_LOG,BLOCK_LOG
     * msg:日志信息
     */
    fun saveLog(type: String, msg: String?) {
        //工厂模式，根据类型获取对应的日志对象
        val iLog = LogFactoryManager.getLogInstance(type)
        //弱引用，减少内存泄漏
        val context = contextRef?.get() ?: return
        //如果不是主线程，也就是子线程中，则直接存储
        if (Looper.getMainLooper().thread.name != Thread.currentThread().name) {
            iLog?.saveLog(context, msg)
        } else {
            //如果是主线程中，则开启线程池来存储
            logTaskExecutor.execute {
                iLog?.saveLog(context, msg)
            }
        }
    }

    fun saveCrashLog(msg: String?) {
        saveLog(CRASH_LOG, msg)
    }

    fun saveBlockLog(msg: String?) {
        saveLog(BLOCK_LOG, msg)
    }

    /**
     * 查询日志
     * onQueryListener：查询日志监听，监听返回的日志信息
     */
    private fun queryLog(type: String, onQueryListener: OnQueryListener) {
        val iLog = LogFactoryManager.getLogInstance(type)
        val context = contextRef?.get() ?: return
        if (Looper.getMainLooper().thread.name != Thread.currentThread().name) {
            onQueryListener.onQuery(iLog?.queryLog(context))
        } else {
            logTaskExecutor.execute {
                onQueryListener.onQuery(iLog?.queryLog(context))
            }
        }
    }

    private fun printLog(type: String? = null) {
        val logType = type ?: LOG
        queryLog(logType, object : OnQueryListener {
            override fun onQuery(queryData: List<Any>?) {
                queryData?.forEach {
                    i(logType, it.toString())
                }
            }
        })
    }

    /**
     * 打印log日志
     */
    fun printLogger() {
        printLog()
    }

    /**
     * 打印异常日志
     */
    fun printCrashLog() {
        printLog(CRASH_LOG)
    }

    /**
     * 打印卡顿日志
     */
    fun printBlockLog() {
        printLog(BLOCK_LOG)
    }


    private fun saveLogger(context: Context?, tag: String, msg: String, level: String) {
        if (Looper.getMainLooper().thread.name != Thread.currentThread().name) {
            logger.saveLoggerLog(context, tag, msg, level)
        } else {
            logTaskExecutor.execute {
                logger.saveLoggerLog(context, tag, msg, level)
            }
        }
    }

    fun startLoggerPage(context: Context) {
        LoggerMsgActivity.startLoggerPage(context)
    }

    fun startCrashPage(context: Context) {
        CrashOrBlockActivity.startCrashPage(context)
    }

    fun startBlockPage(context: Context) {
        CrashOrBlockActivity.startBlockPage(context)
    }

}