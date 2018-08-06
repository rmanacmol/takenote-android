package com.mobileapp.rpm.takenote.view

import android.app.NotificationManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mobileapp.rpm.takenote.R
import com.mobileapp.rpm.takenote.model.History
import com.mobileapp.rpm.takenote.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Timestamp


class MainActivity : AppCompatActivity() {

    private var UNIQUE_ID = 1
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btnPush.setOnClickListener {
            val note: String = et_note.text.toString()
            if (!note.isEmpty()) {
                viewModel?.addHistory(History(note, Timestamp(System.currentTimeMillis()).toString()))
                pushNotif(et_note.text.toString())
                et_note.text?.clear()
                UNIQUE_ID++

            } else {
                this.toast("Please enter note to push")
            }
        }

        btnViewHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

    }

    fun pushNotif(message: String) {
        val notification: NotificationCompat.Builder
        notification = NotificationCompat.Builder(this, "takenote")
        notification.setAutoCancel(true)
        notification.setSmallIcon(R.drawable.ic_event_note)
        notification.setWhen(System.currentTimeMillis())
        notification.setContentTitle(message)
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(UNIQUE_ID, notification.build())
    }

    fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun logUser() {

    }

}
