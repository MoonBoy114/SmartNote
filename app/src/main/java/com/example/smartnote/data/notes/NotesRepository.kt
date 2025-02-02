package com.example.smartnote.data.notes

import com.example.smartnote.Note

class NotesRepository(private val noteDao: NotesDao) {

    suspend fun getAllNotes() = noteDao.getAllNotes()
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)


}