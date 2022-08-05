package com.coder178.notes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coder178.notes.Database.NotesDatabase
import com.coder178.notes.Model.Notes
import com.coder178.notes.Repositiry.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repositiry: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).MyNotesDao()
        repositiry = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) {
        repositiry.insertNotes(notes)
    }

    fun getLowNotes(): LiveData<List<Notes>> = repositiry.getLowNotes()

    fun getMediumNotes(): LiveData<List<Notes>> = repositiry.getMediumNotes()

    fun getHighNotes(): LiveData<List<Notes>> = repositiry.getHighNotes()

    fun getNotes(): LiveData<List<Notes>> = repositiry.getAllNotes()

    fun deleteNotes(id: Int) {
        repositiry.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) {
        repositiry.updateNotes(notes)
    }
}