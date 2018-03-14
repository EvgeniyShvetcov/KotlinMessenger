package com.example.evgeniy.kotlinmessanger.ViewModels

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.evgeniy.kotlinmessanger.MainActivity
import com.example.evgeniy.kotlinmessanger.Models.Application.Companion.clientSocket
import com.example.evgeniy.kotlinmessanger.R
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import kotlinx.android.synthetic.main.login_activity.*


/**
 * Created by evgeniy.shvetcov on 28.02.2018.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

    }

    fun onConnectButtonClick(view: View) {
        val serverAddress = "ws://" + serverAddressEdit.text.toString();
        val userName = userNameEdit.text.toString();
        clientSocket = WebSocketFactory()
                .setConnectionTimeout(5000)
                .createSocket(serverAddress)
                .addListener(object : WebSocketAdapter() {
                    override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("userName", userName);
                        startActivityForResult(intent, 1488)
                    }
                })
                .connectAsynchronously()
    }
}