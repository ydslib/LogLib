package com.yds.loglib

import android.app.Application
import com.yds.liblog.SLog

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SLog.initLog(this, initCrash = true, initBlock = true)
    }

}