package com.example.notesmvvm.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.domain.models.NoteModel
import com.example.domain.usecases.GetCurrentTimeUseCase
import com.example.notesmvvm.R
import com.example.notesmvvm.databinding.FragmentNoteBinding
import com.example.notesmvvm.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val mainViewModel: MainViewModel by activityViewModel()
    private val getCurrentTimeUseCase = GetCurrentTimeUseCase()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        setFragment(NotesListFragment.newInstance(), R.id.placeFragment)
                    }

                    R.id.save -> {
                        val title = binding.noteTitle.text.toString()
                        val description = binding.noteDescription.text.toString()
                        val currentTime = GetCurrentTimeUseCase().execute()
                        var item = mainViewModel.getSelectedItem()
                        if (item != null) {
                            item = item.copy(
                                title = title,
                                description = description,
                                lastTimeUpdate = currentTime
                            )
                            mainViewModel.updateNote(item)
                        }
                        else {
                            item = NoteModel(null, title, description, currentTime)
                            mainViewModel.addNote(item)
                        }
                        setFragment(NotesListFragment.newInstance(), R.id.placeFragment)
                    }
                }
                mainViewModel.setSelectedItem(null)
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = resources.getString(R.string.noteFragmentActionBarTitle)

    }

    private fun init() = with(binding)
    {
        val item = mainViewModel.getSelectedItem()
        Log.d("MyLog", "${item}")
        Log.d("MyLog", "${mainViewModel.notes.value}")
        if (item != null) {
            noteTitle.setText(item.title)
            noteDescription.setText(item.description)
            lastTimeUpdate.setText(item.lastTimeUpdate)
            lastTimeUpdate.visibility = View.VISIBLE
        }
    }

    private fun setFragment(f: Fragment, place: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(place, f)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }

}