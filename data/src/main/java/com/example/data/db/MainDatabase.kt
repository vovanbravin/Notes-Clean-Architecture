package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.NoteDao
import com.example.data.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun newInstance(context: Context): MainDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context,
                    MainDatabase::class.java,
                    "db"
                ).build()
                instance
            }
        }
    }
}