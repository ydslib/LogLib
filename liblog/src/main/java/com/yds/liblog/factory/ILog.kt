package com.yds.liblog.factory

import android.content.Context

interface ILog {

    fun <T> saveLog(context: Context, log: T?)

    fun queryLog(context: Context): List<Any>?

}