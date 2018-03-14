package com.example.evgeniy.kotlinmessanger.Models

import android.app.AlertDialog
import android.telecom.DisconnectCause
import com.example.evgeniy.kotlinmessanger.MainActivity
import com.example.evgeniy.kotlinmessanger.Models.Application.Companion.builder
import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.*


/**
 * Created by Evgeniy on 14.02.2018.
 */
class WebWorker{

    private var Socket: WebSocket;
    private var ref : WeakReference<MainActivity>? = null

    constructor(socket : WebSocket, weakReference: WeakReference<MainActivity>) {
        ref = weakReference
        this.Socket = socket;
    }

    fun Connect(address : String){

        try {
            if(!Socket.isOpen) {
                Socket = WebSocketFactory().createSocket(address, 5000)
                        .addListener(ChatListener(ref))
                        .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                        .connectAsynchronously()
            }
            else {
                Socket.addListener(ChatListener(ref));
            }
        } catch (e: OpeningHandshakeException) {
        } catch (e: HostnameUnverifiedException) {
        } catch (e: WebSocketException) {
        }

    }

    fun Disconnect(){
        Socket.disconnect()
    }

    fun Send(message: Message) {
        val json = Gson().toJson(message);
        Socket.sendText(json.toString())
    }

    class ChatListener(private var weakReference: WeakReference<MainActivity>?) : WebSocketAdapter() {

        override fun onTextMessage(websocket: WebSocket?, text: String?) {
            val message = Gson().fromJson(text, Message::class.java)
            weakReference?.get()?.updateChat(message.Name, message.Text);
        }

        override fun onError(websocket: WebSocket?, cause: WebSocketException?) {

        }

        override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
            if(closedByServer)
            {
                websocket?.sendContinuation();
            }
        }

        override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
            //reconnectChat()
        }
    }
}

