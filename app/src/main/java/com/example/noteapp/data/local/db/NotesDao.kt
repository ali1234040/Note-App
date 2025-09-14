package com.example.noteapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: NoteEntity):Long

    @Query("SELECT * FROM ${MyDataBase.NOTE_TABLE}")
    fun getAllNote(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${MyDataBase.NOTE_TABLE} WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("UPDATE ${MyDataBase.NOTE_TABLE} SET title = :title, `desc` = :desc WHERE id = :id")
    suspend fun updateById(title: String, desc: String, id: Int)

    @Query("DELETE FROM ${MyDataBase.NOTE_TABLE}")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM ${MyDataBase.NOTE_TABLE} WHERE id = :noteId ")
    suspend fun deleteNoteById(noteId: Int)

}