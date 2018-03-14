package com.example.evgeniy.kotlinmessanger.utilis.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.IOException
import java.nio.file.Files.exists



/**
 * Created by evgeniy.shvetcov on 22.02.2018.
 */
class DatabaseManager : SQLiteOpenHelper {
    constructor(context : Context, dataBase : File) : super(context, dataBase.name, null, 1) {
        val folder = dataBase.parentFile
        if (!folder.exists())
            folder.mkdir()
        var exist: Boolean? = true
        try {
            if (!dataBase.exists()) {
                dataBase.createNewFile()
                exist = false
            } else {
                /*dataBase.delete();
                exist = true;*/
            }
        } catch (e: IOException) {
            this.dataBase = null
            //WriteLog("Не удалось создать БД ", null);
        }


        this.dataBase = SQLiteDatabase.openDatabase(dataBase.path, null, SQLiteDatabase.OPEN_READWRITE)

        if (!exist!!) {
            var SQL = "CREATE TABLE Messages "
            SQL += "("
            SQL += " ID VARCHAR(36) PRIMARY KEY NOT NULL, "
            SQL += " Message TEXT, "
            SQL += " Date TEXT"
            SQL += ")"

            this.dataBase?.execSQL(SQL)
        }
    }

    var dataBase : SQLiteDatabase? = null;

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}