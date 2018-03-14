package com.example.evgeniy.kotlinmessanger.Models

import android.location.Location
import com.example.evgeniy.kotlinmessanger.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.neovisionaries.ws.client.WebSocket
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by Evgeniy on 14.02.2018.
 */
class ClientModel {
    var UserName: String
    var ServerService: WebWorker

    constructor(userName : String, webWorker: WebWorker){
        this.UserName = userName;
        this.ServerService = webWorker
    }

    fun connectToServer(serverAddres: String){
        ServerService.Connect(serverAddres);
    }

    fun sendMessage(messageText: String) {
        ServerService.Send(Message(this.UserName, messageText));
    }

}