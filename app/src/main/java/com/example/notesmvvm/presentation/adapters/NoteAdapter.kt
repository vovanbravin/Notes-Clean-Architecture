package com.example.notesmvvm.presentation.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.models.NoteModel
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmvvm.R
import com.example.notesmvvm.databinding.NoteItemBinding

class NoteAdapter(val noteListener: NoteListener): ListAdapter<NoteModel,NoteAdapter.NoteHolder>(NoteCompare()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder.createHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.setData(getItem(position), noteListener)
    }

    class NoteHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val binding = NoteItemBinding.bind(view)

        fun setData(noteModel: NoteModel, listener: NoteListener) = with(binding)
        {
            title.setText(noteModel.title)
            description.setText(noteModel.description)
            itemView.setOnClickListener {
                listener.onClickItem(noteModel)
            }
        }

        companion object {
            fun createHolder(parent: ViewGroup) : NoteHolder
            {
                return NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent,false))
            }
        }
    }

    class NoteCompare(): DiffUtil.ItemCallback<NoteModel>()
    {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

    }

     interface NoteListener{
         fun onClickItem(noteModel: NoteModel)
     }


}