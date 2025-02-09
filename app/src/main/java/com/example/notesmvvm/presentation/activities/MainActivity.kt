package com.example.notesmvvm.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.domain.models.NoteModel
import com.example.domain.usecases.AddNoteUseCase
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNotesUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import com.example.notesmvvm.R
import com.example.notesmvvm.presentation.fragments.NoteFragment
import com.example.notesmvvm.presentation.fragments.NotesListFragment
import com.example.notesmvvm.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(NotesListFragment.newInstance(), R.id.placeFragment)

    }

    private fun setFragment(f: Fragment, place: Int)
    {
        supportFragmentManager.beginTransaction()
            .replace(place, f).commit()
    }

}