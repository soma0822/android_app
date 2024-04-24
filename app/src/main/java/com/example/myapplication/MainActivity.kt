package com.example.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import android.view.View
import android.widget.Toast
import service.VirtualSensorService
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var mService: IVirtualSensorService? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            mService = IVirtualSensorService.Stub.asInterface(binder)
            // Serviceから値を取得してUIに表示する
            displaySensorValue()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = Intent(this, VirtualSensorService::class.java)
        bindService(intent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }

    // ボタンがクリックされたときの処理
    fun onGetSensorValueButtonClick(view: View) {
        // Serviceから値を取得してUIに表示する
        displaySensorValue()
    }

    // Serviceから値を取得してUIに表示するメソッド
    private fun displaySensorValue() {
        // Serviceがバインドされているか確認
        if (mService != null) {
            // Serviceから値を取得する
            val sensorValue = mService?.getSensorValue()
            // 取得した値をUIに表示する
            val textView = findViewById<TextView>(R.id.textView)
            textView.text = "Sensor Value: $sensorValue"
        } else {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show()
        }
    }
}
