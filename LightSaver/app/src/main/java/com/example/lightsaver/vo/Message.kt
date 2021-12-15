package com.example.lightsaver.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Messages")
class Message {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null

    @ColumnInfo(name = "message")
    var message: String? = null

    @ColumnInfo(name = "value")
    var value: String? = null
}