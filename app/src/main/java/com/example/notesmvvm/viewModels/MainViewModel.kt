package com.example.notesmvvm.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.NoteModel
import com.example.domain.usecases.AddNoteUseCase
import com.example.domain.usecases.DeleteAllNotesUseCase
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNotesUseCase
import com.example.domain.usecases.SearchNoteByTitleUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase
): ViewModel(){

    private var _notes = MutableStateFlow<List<NoteModel>>(listOf())

    val notes = _notes.asStateFlow()

    private var selectedItem: NoteModel? = null

    init{
        viewModelScope.launch {
            getAllNotesUseCase.execute().collect{
                _notes.value = it
            }
        }
    }

    fun addNote(noteModel: NoteModel) = viewModelScope.launch {
        addNoteUseCase.execute(noteModel)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        deleteNoteUseCase.execute(id)
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch {
        updateNoteUseCase.execute(noteModel)
    }

    fun deleteAllNotes() = viewModelScope.launch{
        if(notes.value.isNotEmpty())
            deleteAllNotesUseCase.execute()
    }

    fun setSelectedItem(selectedItem: NoteModel?)
    {
        this.selectedItem = selectedItem
    }

    fun getSelectedItem(): NoteModel?
    {
        return this.selectedItem
    }

}