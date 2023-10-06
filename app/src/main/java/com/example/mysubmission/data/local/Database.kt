package com.example.mysubmission.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mysubmission.data.model.ItemsItem

@Database(entities = [ItemsItem::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun userDao(): RoomDao
}