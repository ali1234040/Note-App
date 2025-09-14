package com.example.noteapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class],
    version = MyDataBase.VERSION,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object{
        const val VERSION = 1
        const val DB_NAME = "school_db"
        const val NOTE_TABLE = "note"
    }

}