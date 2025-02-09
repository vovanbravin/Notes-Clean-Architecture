package com.example.notesmvvm.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.models.NoteModel
import com.example.domain.usecases.SearchNoteByTitleUseCase
import com.example.notesmvvm.R
import com.example.notesmvvm.databinding.FragmentNotesBinding
import com.example.notesmvvm.di.appModule
import com.example.notesmvvm.presentation.adapters.NoteAdapter
import com.example.notesmvvm.viewModels.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotesListFragment : Fragment(), View.OnClickListener, NoteAdapter.NoteListener{
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NoteAdapter
    private val mainViewModel: MainViewModel by activityViewModel()
    private val searchNoteByTitleUseCase = SearchNoteByTitleUseCase()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        createFragmentMenu()
    }

    private fun init() = with(binding)
    {
        val swipeHelper = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mainViewModel.deleteNote(mainViewModel.notes.value.get(position).id!!)
            }
        }

        rcView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = NoteAdapter(this@NotesListFragment)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(rcView)
        rcView.adapter = adapter

        add.setOnClickListener(this@NotesListFragment)
    }

    private fun createFragmentMenu()
    {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_list_menu, menu)

                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView

                searchView.queryHint = resources.getString(R.string.query_hint_searchView)

                searchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapter.submitList(searchNoteByTitleUseCase.execute(newText!!,mainViewModel.notes.value))
                        return true
                    }
                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId)
                {
                    R.id.delete_all-> {
                        showDialog(requireContext())
                    }
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = resources.getString(R.string.app_name)

    }
    override fun onClickItem(noteModel: NoteModel) {
        mainViewModel.setSelectedItem(noteModel)
        setFragment(NoteFragment.newInstance(), R.id.placeFragment)
    }

    private fun showDialog(context: Context)
    {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle(resources.getString(R.string.title_dialog))
            .setMessage(resources.getString(R.string.message_dialog))
            .setPositiveButton(resources.getString(R.string.text_positive_button_dialog)) { dialog, _->
                mainViewModel.deleteAllNotes()
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.text_negative_button_dialog)){dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()

    }

    private fun subscribe()
    {
        lifecycleScope.launch {
            mainViewModel.notes.collect{
                adapter.submitList(it)
                Log.d("MyLog", "${it}")
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.add->
            {
                setFragment(NoteFragment.newInstance(), R.id.placeFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }

    private fun setFragment(f: Fragment, place: Int)
    {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(place, f)?.commit()
    }
}