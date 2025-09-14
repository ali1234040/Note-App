package com.example.noteapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.local.db.NoteEntity
import com.example.noteapp.data.local.db.NotesDao
import com.example.noteapp.data.local.db.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (
    private val notesDao: NotesDao,
    private val repository: NotesRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    init {
        getAllNotes()
    }

    fun addNote(note: NoteEntity){
        viewModelScope.launch {
            notesDao.insertNote(NoteEntity(
                title = note.title,
                desc = note.desc,
                date = repository.getPersianDate()))
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            notesDao.getAllNote().collectLatest {
                _notes.value = it
            }
        }
    }

    fun getNoteById(id : Int, onResult:(note : NoteEntity) -> Unit) {
        viewModelScope.launch {
           val note = notesDao.getNoteById(id) ?: NoteEntity(0 , "","","")
            onResult(note)
        }
    }


    fun deleteAllNote() {
        viewModelScope.launch {
            val result =
                try {
                    notesDao.deleteAllNotes()
                    true
                } catch (e: Exception) {
                    Log.i("ROOM_ERROR", e.message.toString())
                    false
                }
            _message.emit(
                if (result) "یادداشت حذف شد" else "حذف ناموفق بود"
            )
        }
    }

    fun deleteNoteById(id: Int) {
        viewModelScope.launch {
            notesDao.deleteNoteById(id)
        }
    }

    fun updateNote(note: NoteEntity){
        viewModelScope.launch {
            notesDao.updateById(title = note.title, desc = note.desc, id = note.id)
        }
    }

}
