package com.hy.note.ui

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.hy.common.base.BaseFragment
import com.hy.common.model.Note
import com.hy.common.widget.DicPopupWin
import com.hy.note.R
import com.hy.note.databinding.FragmentNoteListBinding
import com.hy.note.widget.NoteDiffItemCallback
import kotlinx.android.synthetic.main.fragment_note_list.*


/**
 * @Author Lenovo
 */
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    companion object {
        const val TAG = "NoteListFragment";
    }

    lateinit var noteListiewModel: NoteListViewModel;
    var dicPopupWindow: DicPopupWin? = null
    private val noteListAdapter by lazy {
        NoteListAdapter()
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

        dic_note_ll.setOnClickListener {
           // if(dicPopupWindow==null){
            dic_arrow_iv.setImageResource(R.mipmap.arrow_down_bold)
            dicPopupWindow = DicPopupWin(0, activity) { type ->
                //重新刷新数据
                type_name_tv.text = type?.content
                refreshData(type?.id)
            }
            dicPopupWindow!!.setOnDismissListener {
                dic_arrow_iv.setImageResource(R.mipmap.arrow_up_bold)
            }
            dicPopupWindow!!.show(it)
        }

        noteListAdapter.menuItemListener = object : NoteListAdapter.onMenuItemListener {
            override fun deleteItem(position: Int, note: Note?) {
                noteListiewModel.deleteNote(note)
            }

            override fun toListOfTop() {
                //todo
            }

            override fun moteToOtherDic() {
                //todo
            }

            override fun onItemClick(note: Note?) {
                note?.let {
                    noteListiewModel.editPage(it.id.toInt())
                }
            }

        }
        noteListiewModel.deleteNote.observe(this, Observer {
            if (it != null) {
                val olds = noteListAdapter.notes?.toMutableList()
                noteListAdapter.notes?.remove(it)
                val recycItemCallback =
                    NoteDiffItemCallback(
                        olds!!,
                        noteListAdapter.notes!!
                    )
                val diffResult = DiffUtil.calculateDiff(recycItemCallback, true)
                diffResult.dispatchUpdatesTo(noteListAdapter)
                noteListAdapter.notes?.isEmpty()?.let { it1 -> emptyView(it1) }
            } else {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun initData() {
        requestData()
        ElegantBus.getDefault("saveState")
            .observe(this, object : ObserverWrapper<Any>() {
                override fun onChanged(value: Any?) {
                    requestData()
                }
            })
        noteListiewModel.notesLiveData?.observe(viewLifecycleOwner, Observer {
            noteListAdapter.apply {
                if (it.isNullOrEmpty()) {
                    clearData()
                    notifyDataSetChanged()
                } else {
                    val olds = if (notes == null) mutableListOf() else notes
                    diffRefresh(olds!!, it as MutableList<Note>)
                    notes = it
                }
                emptyView(it.isEmpty())
            }
        })
    }

    fun emptyView(empty: Boolean) {
        if (empty) {
            ll_empty.visibility = View.VISIBLE
        } else {
            ll_empty.visibility = View.GONE
        }
    }

    fun diffRefresh(olds: MutableList<Note>, news: MutableList<Note>) {
        val recycItemCallback =
            NoteDiffItemCallback(
                olds,
                news
            )
        val diffResult = DiffUtil.calculateDiff(recycItemCallback, true)
        diffResult.dispatchUpdatesTo(noteListAdapter)
    }

    fun requestData() {
        refreshData(type = -1)
    }

    fun refreshData(type: Int?) {
        if (type != null) {
            noteListiewModel.getNotes(type)
        }
    }

}
