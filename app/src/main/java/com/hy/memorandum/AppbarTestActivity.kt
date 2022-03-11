package com.hy.memorandum

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_appbar_test.*

class AppbarTestActivity : AppCompatActivity() {

    private var msg_log_recyc: RecyclerView? = null
    var msgEvents: MutableList<MsgEvent> = mutableListOf()
    var msgLogAdapter: MsgLogAdapter? = null
    var titlebar: Toolbar? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbar_test)

        toolbar.inflateMenu(R.menu.menu_note);
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.view_grid -> Toast.makeText(
                    this@AppbarTestActivity,
                    "宫格视图",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
        init()
        msg_log_recyc = findViewById(R.id.recyclerview)
        msgLogAdapter = MsgLogAdapter()
        msg_log_recyc?.layoutManager = LinearLayoutManager(this)
        msg_log_recyc?.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        msg_log_recyc?.adapter = msgLogAdapter

        titlebar = findViewById(R.id.toolbar)
        titlebar?.setSubtitle("一起摇摆")
        titlebar?.setTitleTextColor(Color.RED)

    }

    fun init() {
        for (index in 1..30) {
            val msgEvent = MsgEvent("测试的数据", -1, "123456")
            msgEvents.add(msgEvent)
        }
    }

    data class MsgEvent(val content: String?, val status: Int, val deviceSerial: String?)

    inner class MsgLogAdapter : RecyclerView.Adapter<MsgLogAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var contentTv: TextView? = null
            var deviceSerialTv: TextView? = null

            init {
                contentTv = itemView.findViewById(R.id.msg_content)
                deviceSerialTv = itemView.findViewById(R.id.msg_device)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(this@AppbarTestActivity).inflate(R.layout.msg_log, null, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val msgEvent = msgEvents[position]
            val contentTv = holder.contentTv
            if (msgEvent.status == -1) {
                contentTv?.setTextColor(Color.BLACK)
                contentTv?.setText("消息:" + msgEvent.content)
                holder.deviceSerialTv?.setText("设备:" + msgEvent.deviceSerial)
            } else {
                holder.deviceSerialTv?.setText("")
                if (msgEvent.status == 5) {
                    contentTv?.setText("长连接异常 ============状态码:" + msgEvent.status)
                    contentTv?.setTextColor(Color.RED)
                } else {
                    contentTv?.setTextColor(Color.BLACK)
                    contentTv?.setText("长连接正常 ============状态码:" + msgEvent.status)
                }

            }
        }

        override fun getItemCount(): Int {
            return msgEvents.size
        }

    }
}