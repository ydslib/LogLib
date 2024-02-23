package com.yds.liblog.factory

class LoggerFactory : LogFactory {
    override fun createLogFactory(): ILog {
        return Logger()
    }
}