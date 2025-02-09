package com.example.notesmvvm.di

import com.example.domain.usecases.AddNoteUseCase
import com.example.domain.usecases.DeleteAllNotesUseCase
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNotesUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import org.koin.dsl.module


val domainModule = module {
    factory<AddNoteUseCase> {
        AddNoteUseCase(noteRepository = get())
    }
    factory<DeleteNoteUseCase> {
        DeleteNoteUseCase(noteRepository = get())
    }
    factory<UpdateNoteUseCase> {
        UpdateNoteUseCase(noteRepository = get())
    }
    factory<GetAllNotesUseCase> {
        GetAllNotesUseCase(noteRepository = get())
    }
    factory<DeleteAllNotesUseCase>{
        DeleteAllNotesUseCase(noteRepository = get())
    }
}
