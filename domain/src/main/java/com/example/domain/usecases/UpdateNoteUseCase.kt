package com.example.domain.usecases

import com.example.domain.models.NoteModel
import com.example.domain.repository.NoteRepository

class UpdateNoteUseCase(private val noteRepository: NoteRepository){

    suspend fun execute(noteModel: NoteModel)
    {
        noteRepository.updateNote(noteModel)
    }
}