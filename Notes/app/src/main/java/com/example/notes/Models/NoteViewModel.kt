package com.example.notes.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.Database.NoteDatabase
import com.example.notes.Database.NotesRepositoty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repositoty: NotesRepositoty

    val allnotes : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repositoty = NotesRepositoty(dao)
        allnotes = repositoty.allNotes
    }
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){

        repositoty.delete(note)
    }
    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repositoty.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repositoty.update(note)
    }
}