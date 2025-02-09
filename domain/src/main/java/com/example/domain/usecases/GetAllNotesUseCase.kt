package com.example.domain.usecases

import com.example.domain.models.NoteModel
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(): Flow<List<NoteModel>>
    {
        return noteRepository.getAllNotes()
    }
}