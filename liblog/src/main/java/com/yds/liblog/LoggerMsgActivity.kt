package com.yds.liblog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class LoggerMsgActivity : AppCompatActivity() {

    var viewModel: LogViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_msg)

        viewModel = ViewModelProvider(this)[LogViewModel::class.java]

        viewModel?.loggerLiveData?.observe(this) {
            if (!it.isNullOrEmpty()) {

            }
        }

    }

    companion object {
        fun startLoggerPage(context: Context) {
            val intent = Intent(context, LoggerMsgActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }


}