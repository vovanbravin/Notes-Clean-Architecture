package com.example.domain.repository

import com.example.domain.models.NoteModel
import kotlinx.coroutines.flow.Flow


interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<NoteModel>>

    suspend fun addNote(noteModel: NoteModel)

    suspend fun deleteNote(id: Int)

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun deleteAllNotes()

}