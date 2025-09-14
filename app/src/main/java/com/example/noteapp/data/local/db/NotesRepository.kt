package com.example.noteapp.data.local.db

import androidx.room.util.findAndInstantiateDatabaseImpl
import com.example.noteapp.base.PersianDate
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val date: PersianDate
){

    fun getPersianDate() =
        "${date.year}/${date.month}/${date.day} | ${date.hour}:${date.min}"

}