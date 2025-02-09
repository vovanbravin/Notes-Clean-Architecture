package com.example.domain.usecases

import com.example.domain.models.NoteModel
import com.example.domain.repository.NoteRepository

class SearchNoteByTitleUseCase() {

    fun execute(title: String, notes: List<NoteModel>): List<NoteModel>
    {
        if(notes.isEmpty()) return emptyList()
        return notes.filter {note->
            note.title.contains(title)
        }
    }
}