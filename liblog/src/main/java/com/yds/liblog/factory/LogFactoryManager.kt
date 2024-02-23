package com.yds.liblog.factory

import com.yds.liblog.SLog

object LogFactoryManager {

    fun getLogInstance(type: String?): ILog? {
        return when (type) {
            SLog.BLOCK_LOG -> {
                BlockLogFactory().createLogFactory()
            }

            SLog.CRASH_LOG -> {
                CrashLogFactory().createLogFactory()
            }

            SLog.LOG -> {
                LoggerFactory().createLogFactory()
            }

            else -> {
                null
            }
        }
    }

}