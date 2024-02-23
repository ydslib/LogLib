package com.yds.liblog.factory

import com.yds.liblog.db.BlockModel

class BlockLogFactory : LogFactory {
    override fun createLogFactory(): ILog {
        return BlockLog()
    }
}