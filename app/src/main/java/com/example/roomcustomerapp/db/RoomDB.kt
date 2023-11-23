package com.example.roomcustomerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomcustomerapp.db.daos.CountryDao
import com.example.roomcustomerapp.db.daos.CustomerDao
import com.example.roomcustomerapp.db.entities.CountryEntity
import com.example.roomcustomerapp.db.entities.CustomerEntity


@Database(entities = [CountryEntity::class, CustomerEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun customerDao(): CustomerDao

    companion object {
        private var INSTANCE: RoomDB? = null
        fun accessDB(context: Context): RoomDB? {
            if (INSTANCE == null) {
                synchronized(RoomDB::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, RoomDB::class.java, "task")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}