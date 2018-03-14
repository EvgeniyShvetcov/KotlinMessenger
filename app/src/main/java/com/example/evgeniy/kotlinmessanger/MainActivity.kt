package com.example.evgeniy.kotlinmessanger

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.evgeniy.kotlinmessanger.Models.*
import com.example.evgeniy.kotlinmessanger.Models.Application.Companion.clientSocket
import com.example.evgeniy.kotlinmessanger.databinding.ActivityMainBinding
import com.example.evgeniy.kotlinmessanger.utilis.DAO.MessageDAO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

    private lateinit var adapter : RecyclerViewAdapter;
    private lateinit var messageDataAccess : MessageDAO;
    private lateinit var client : ClientModel;
    private lateinit var fusedLocationClient : FusedLocationProviderClient;
    private var currentLocation : Location? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Initialization()
    }

    override fun onDestroy() {
        super.onDestroy()
        Application.clientSocket.disconnect()
    }

    private fun Initialization() {
        val userName = intent.getStringExtra("userName");
        client = ClientModel(userName, WebWorker(clientSocket, WeakReference(this)));
        client.connectToServer("");

        //Расположение элементов линейное
        val layoutManager = LinearLayoutManager(this)
        RecyclerListView.layoutManager = layoutManager

        messageDataAccess = MessageDAO()
        val messages = messageDataAccess.GetAll()

        //Задаём адаптер который хранит данные
        adapter = RecyclerViewAdapter(messages)
        RecyclerListView.adapter = adapter

        Application.builder = AlertDialog.Builder(this);

        //Обработка и получение текущего местоположения
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1488);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            this.currentLocation = location;
        }

    }

    fun onSendMessageButtonClick(view : View?) {
        var textMessage = MessageTextEdit.text?.toString()
        client.sendMessage(textMessage.orEmpty());
        //client.sendMessage(message, UUID.randomUUID())

        //messageDataAccess.Save(message)

        MessageTextEdit.setText("")

    }

    fun onAddAtachementsButtonClick(view : View?) {
        var message = "";
        try {
            val task = fusedLocationClient.lastLocation;
            currentLocation = task.result
            message = "Текущее положение: " + currentLocation?.latitude + "," + currentLocation?.longitude;
        }
        catch (e: SecurityException) {

        }
        finally {
            MessageTextEdit.setText(message);
        }
    }

    fun updateChat(userName: String, text: String) {
        adapter.DataSet?.add(Message(userName, text));
        adapter.notifyDataSetChanged()
    }
}
