package com.example.evgeniy.kotlinmessanger.Models

import android.app.AlertDialog
import android.app.Application
import com.example.evgeniy.kotlinmessanger.Models.Application.Companion.databaseManager
import com.example.evgeniy.kotlinmessanger.utilis.database.DatabaseManager
import com.neovisionaries.ws.client.WebSocket
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Evgeniy on 14.02.2018.
 */
class Application : Application() {
    companion object {
        lateinit var DateFormat: SimpleDateFormat
        lateinit var builder : AlertDialog.Builder;
        lateinit var databaseManager: DatabaseManager
        lateinit var clientSocket: WebSocket;
    }

    /*************************************************************
     * Запуск приложения.
     */
    override fun onCreate() {
        super.onCreate()
        //Объект для конвертации дат в строку в форматированном виде и обратно
        DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ru-RU"))
        var database = File(applicationInfo.dataDir + "/Database")
        if (!database.exists())
            database.mkdir()
        database = File(database.toString() + "/database.db")
        databaseManager = DatabaseManager(applicationContext, database)

    }
}