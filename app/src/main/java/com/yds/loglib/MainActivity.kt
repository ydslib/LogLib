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
            SLog.i("MainActivity", "test", true)
            SLog.printLogger()
        }

        findViewById<Button>(R.id.block_log).setOnClickListener {
            SLog.saveLog(SLog.BLOCK_LOG, "block_test")
            SLog.startBlockPage(this)

        }

        findViewById<Button>(R.id.crash_log).setOnClickListener {
            SLog.saveLog(SLog.CRASH_LOG, "crash_log")
            SLog.startCrashPage(this)
        }
    }
}