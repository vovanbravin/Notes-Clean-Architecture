package com.example.notesmvvm.di

import com.example.data.NoteRepositoryImpl
import com.example.data.dao.NoteDao
import com.example.data.db.MainDatabase
import com.example.domain.repository.NoteRepository
import org.koin.dsl.module


val dataModule = module {
    single<MainDatabase> {
        MainDatabase.newInstance(context = get())
    }
    single<NoteDao>{
        MainDatabase.newInstance(context = get()).getDao()
    }
    single<NoteRepository>{
        NoteRepositoryImpl(dao = get())
    }

}
