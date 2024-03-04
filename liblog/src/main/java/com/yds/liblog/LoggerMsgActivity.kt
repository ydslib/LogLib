package com.yds.liblog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yds.liblog.adapter.LoggerAdapter

class LoggerMsgActivity : AppCompatActivity() {

    private var viewModel: LogViewModel? = null

    private val loggerAdapter by lazy {
        LoggerAdapter(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liblog_activity_log_msg)

        viewModel = ViewModelProvider(this)[LogViewModel::class.java]

        viewModel?.loggerLiveData?.observe(this) {
            if (!it.isNullOrEmpty()) {
                loggerAdapter.dataList.clear()
                loggerAdapter.dataList.addAll(it)
                loggerAdapter.notifyDataSetChanged()
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.liblog_recycler)
        recyclerView.adapter = loggerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val title = findViewById<TextView>(R.id.liblog_title)
        title.text = "Logger Log"
        val back = findViewById<ImageView>(R.id.liblog_back)
        back.setOnClickListener {
            finish()
        }

        viewModel?.queryLoggerLog(this)

    }

    companion object {
        fun startLoggerPage(context: Context) {
            val intent = Intent(context, LoggerMsgActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }


}