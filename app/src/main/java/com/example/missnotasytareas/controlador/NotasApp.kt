package com.example.missnotasytareas.controlador

import android.app.Application
import androidx.room.Room

class NotasApp :Application() {
    companion object{
        lateinit var db: NotasDB
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            NotasDB::class.java,
            "Notas"
        ).build()
    }
}