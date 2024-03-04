package com.yds.liblog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yds.liblog.adapter.ButtonItemDelegate
import com.yds.liblog.adapter.MultipleTypeAdapter

class LiblogEntryActivity : AppCompatActivity() {

    companion object {
        fun startLogPageEntry(context: Context) {
            val intent = Intent(context, LiblogEntryActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liblog_activity_entry)

        val attr = window.attributes
        attr.gravity = Gravity.BOTTOM
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, -2)

        initRecycler()

        val back = findViewById<ImageView>(R.id.liblog_back)
        back.setOnClickListener {
            finish()
        }



    }

    private fun initRecycler(){
        val recyclerView = findViewById<RecyclerView>(R.id.liblog_recycler)

        val behavior = BottomSheetBehavior.from(recyclerView)
        behavior.setPeekHeight(getDisplayMetrics(this).heightPixels / 2, false)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MultipleTypeAdapter()
        adapter.addItem(ButtonItemDelegate("普通日志") {
            SLog.startLoggerPage(this@LiblogEntryActivity)
        })
        adapter.addItem(ButtonItemDelegate("异常日志") {
            SLog.startCrashPage(this@LiblogEntryActivity)
        })
        adapter.addItem(ButtonItemDelegate("卡顿日志") {
            SLog.startBlockPage(this@LiblogEntryActivity)
        })

        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(metrics)
        return metrics
    }

}