package com.example.evgeniy.kotlinmessanger.utilis.DAO

import android.app.AlertDialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.evgeniy.kotlinmessanger.Models.Application
import com.example.evgeniy.kotlinmessanger.Models.Message
import java.text.ParseException
import java.util.*


/**
 * Created by evgeniy.shvetcov on 22.02.2018.
 */
class MessageDAO {

    /*****************************************************
     * Метод для сохранения сообщения в БД
     * @param message Сохраняемое в базу сообщение
     */
    fun Save(message: Message) {
        try {
            val SQL = "insert into Messages (ID, Message, Date) values " + "(?, ?, ?)"
            val statement = Application.databaseManager.dataBase?.compileStatement(SQL)
            //statement?.bindString(1, message.Id.toString())
            //statement?.bindString(2, message.Text)
            //statement?.bindString(3, Application.DateFormat.format(message.Date))

            statement?.execute()

        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun GetAll(): ArrayList<Message> {
        val SQL = "select * from Messages order by Date ASC;"
        var cursor: Cursor? = null

        val messagesArray = ArrayList<Message>()
        var message: Message
        var str: String?
        var date: Date
        var id: UUID
        try {
            cursor = Application.databaseManager.dataBase?.rawQuery(SQL, null)
            cursor?.moveToFirst()

            while (cursor?.moveToNext()!!){
                id = UUID.fromString(cursor?.getString(0))
                str = cursor?.getString(1)
                date = Application.DateFormat.parse(cursor?.getString(2))

               // message = Message(id, str, date)

                //messagesArray.add(message)

            }
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (cursor != null)
                cursor.close()
        }

        return messagesArray
    }

    fun DeleteHistory() {
        val dataBase: SQLiteDatabase? = Application.databaseManager.dataBase
        try {
            val SQL = "delete from Messages"
            dataBase?.beginTransaction()
            dataBase?.execSQL(SQL)
            dataBase?.endTransaction()
        }
        finally {

        }

    }
}