package com.example.lightsaver.persistence

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.lightsaver.vo.Message

/**
 * The Room database that contains the Messages table
 */
@Database(entities = [(Message::class)], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        @JvmStatic fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "esp8266_connect.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}