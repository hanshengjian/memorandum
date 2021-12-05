package com.hy.note.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cody.bus.ElegantBus
import cody.bus.ElegantLog
import cody.bus.ObserverWrapper
import com.hy.common.base.BaseFragment
import com.hy.note.R
import com.hy.note.databinding.FragmentNoteListBinding


/**
 * @Author Lenovo
 */
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {
    lateinit var noteListiewModel: NoteListViewModel;
    private val noteListAdapter by lazy {
        NoteListAdapter(){
            noteListiewModel.editPage(it.id.toInt())
        }
    }

    override fun onCreateLayout(): Int {
        return R.layout.fragment_note_list
    }

    override fun initView(root: View) {
        noteListiewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        binding.apply {
            viewModel = noteListiewModel
            adapter = noteListAdapter
        }
    }
    override fun initData() {
        requestData()
        ElegantBus.getDefault("saveState")
            .observe(this, object : ObserverWrapper<Any>() {
                override fun onChanged(value: Any?) {
                    requestData()
                }
            })
    }

    fun requestData(){
        noteListiewModel.getNotes()?.observe(viewLifecycleOwner, Observer {
            noteListAdapter.apply {
                notes = it
                if(it.size>0){
                    notifyDataSetChanged()
                }
            }
        })
    }

}
