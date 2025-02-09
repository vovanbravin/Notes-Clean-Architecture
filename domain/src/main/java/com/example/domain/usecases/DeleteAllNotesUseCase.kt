package com.example.domain.usecases

import com.example.domain.repository.NoteRepository

class DeleteAllNotesUseCase(val noteRepository: NoteRepository) {

    suspend fun execute() {
        noteRepository.deleteAllNotes()
    }
}