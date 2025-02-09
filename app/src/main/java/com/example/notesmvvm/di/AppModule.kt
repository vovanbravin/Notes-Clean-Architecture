package com.example.notesmvvm.di

import com.example.notesmvvm.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel<MainViewModel>{
        MainViewModel(
            addNoteUseCase = get(),
            deleteNoteUseCase =  get(),
            updateNoteUseCase =  get(),
            getAllNotesUseCase =  get(),
            deleteAllNotesUseCase = get()
        )
    }
}