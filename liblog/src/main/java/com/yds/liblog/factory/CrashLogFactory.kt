package com.yds.liblog.factory

import com.yds.liblog.db.CrashModel

class CrashLogFactory : LogFactory {

    override fun createLogFactory(): ILog {
        return CrashLog()
    }
}