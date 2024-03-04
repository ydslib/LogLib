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
import com.yds.liblog.adapter.CrashOrBlockAdapter

class CrashOrBlockActivity : AppCompatActivity() {

    private val mAdapter by lazy { CrashOrBlockAdapter(mutableListOf()) }

    private var viewModel: LogViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liblog_activity_log_msg)

        viewModel = ViewModelProvider(this)[LogViewModel::class.java]

        val title = findViewById<TextView>(R.id.liblog_title)
        val back = findViewById<ImageView>(R.id.liblog_back)
        val recycler = findViewById<RecyclerView>(R.id.liblog_recycler)


        val logType = intent.extras?.getString(LOG_TYPE)
        when (logType) {
            SLog.CRASH_LOG -> {
                title.text = "Crash Log"
                viewModel?.crashLiveData?.observe(this) {
                    if (!it.isNullOrEmpty()) {
                        mAdapter.dataList.clear()
                        mAdapter.dataList.addAll(it)
                        mAdapter.notifyDataSetChanged()
                    }
                }
                viewModel?.queryCrashLog(this@CrashOrBlockActivity)
            }

            SLog.BLOCK_LOG -> {
                title.text = "Block Log"
                viewModel?.blockLiveData?.observe(this) {
                    if (!it.isNullOrEmpty()) {
                        mAdapter.dataList.clear()
                        mAdapter.dataList.addAll(it)
                        mAdapter.notifyDataSetChanged()
                    }
                }
                viewModel?.queryBlockLog(this@CrashOrBlockActivity)
            }
        }

        back.setOnClickListener {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mAdapter

    }

    companion object {
        private const val LOG_TYPE = "log_type"
        fun startCrashPage(context: Context) {
            val bundle = Bundle()
            bundle.putString(LOG_TYPE, SLog.CRASH_LOG)
            startCrashOrBlockPage(context, bundle)
        }

        fun startBlockPage(context: Context) {
            val bundle = Bundle()
            bundle.putString(LOG_TYPE, SLog.BLOCK_LOG)
            startCrashOrBlockPage(context, bundle)
        }

        private fun startCrashOrBlockPage(context: Context, bundle: Bundle) {
            val intent = Intent(context, CrashOrBlockActivity::class.java)
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}