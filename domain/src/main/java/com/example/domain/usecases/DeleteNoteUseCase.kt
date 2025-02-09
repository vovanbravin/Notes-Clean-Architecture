package com.example.domain.usecases

import com.example.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(id: Int)
    {
        noteRepository.deleteNote(id)
    }
}