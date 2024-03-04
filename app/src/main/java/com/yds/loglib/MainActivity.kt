package com.yds.loglib

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yds.liblog.SLog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        findViewById<Button>(R.id.log).setOnClickListener {
//            SLog.v("MainActivity", "test", true)
//            SLog.d("MainActivity", "test", true)
//            SLog.w("MainActivity", "test", true)
            SLog.e("MainActivity", "fedsfdsdfdsfdsfdsfdsdfsdfsdfsdfsdfsdfdfsfdfedsfdsdfdsfdsfdsfdsdfsdfsdfsdfsdfsdfdfsfd", true)
            SLog.startLoggerPage(this)
        }

        findViewById<Button>(R.id.block_log).setOnClickListener {
            SLog.saveBlockLog("block_test")
            SLog.startBlockPage(this)

        }

        findViewById<Button>(R.id.crash_log).setOnClickListener {
            try {
                2 / 0
            } catch (e: Exception) {
                SLog.saveCrashLog(e.stackTraceToString())
            }
            SLog.startCrashPage(this)
        }

        findViewById<Button>(R.id.log_entry).setOnClickListener {
            SLog.startLogEntry(this@MainActivity)
        }
    }
}