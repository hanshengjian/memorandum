package com.hy.note.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hy.common.model.Note
import com.hy.note.R
import com.hy.note.databinding.ItemNoteBinding

/**
 * @Author Lenovo
 */
class NoteListAdapter(var block:(note:Note)->Unit) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    var notes:List<Note>?=null

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var viewDataBinding: ItemNoteBinding;

        fun setContent(note:Note){
            viewDataBinding.note = note
          //  viewDataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ItemNoteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_note,
            parent,
            false
        )
        return ViewHolder(dataBinding.root).apply {
            viewDataBinding = dataBinding
        }
    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        notes?.get(position)?.let { holder.setContent(it) }
        holder.itemView.setOnClickListener {
            block.invoke(notes?.get(position)!!)
        }
    }


}