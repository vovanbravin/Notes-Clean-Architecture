package com.example.domain.usecases

import com.example.domain.models.NoteModel
import com.example.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository){

    suspend fun execute(noteModel: NoteModel)
    {
        noteRepository.addNote(noteModel)
    }

}