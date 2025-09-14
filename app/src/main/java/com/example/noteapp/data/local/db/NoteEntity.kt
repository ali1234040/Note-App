package com.example.noteapp.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MyDataBase.NOTE_TABLE)
class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val date: String
)
