package com.example.data

import androidx.room.util.newStringBuilder
import com.example.data.dao.NoteDao
import com.example.data.entities.Note
import com.example.domain.models.NoteModel
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(val dao: NoteDao): NoteRepository {

    override suspend fun getAllNotes(): Flow<List<NoteModel>> {
        val notes = dao.getAllNotes()
        return notes.map{ list->
            list.map {note->
                mapToDomain(note)
            }
        }
    }

    override suspend fun addNote(noteModel: NoteModel) {
        val note = mapToData(noteModel)
        dao.addNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }

    override suspend fun updateNote(noteModel: NoteModel) {
        val note = mapToData(noteModel)
        dao.updateNote(note)
    }


    override suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }


    private fun mapToData(noteModel: NoteModel): Note
    {
        return Note(id = noteModel.id,
            title = noteModel.title,
            description = noteModel.description,
            lastTimeUpdate = noteModel.lastTimeUpdate)
    }

    private fun mapToDomain(note: Note): NoteModel
    {
        return NoteModel(id = note.id, title = note.title, description = note.description, lastTimeUpdate = note.lastTimeUpdate)
    }

}