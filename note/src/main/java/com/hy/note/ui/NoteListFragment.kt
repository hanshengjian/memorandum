package com.hy.note.ui

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.hy.common.base.BaseFragment
import com.hy.common.eventbus.RefreshNote
import com.hy.common.model.Note
import com.hy.common.widget.DicPopupWin
import com.hy.note.R
import com.hy.note.databinding.FragmentNoteListBinding
import com.hy.note.widget.NoteDiffItemCallback
import kotlinx.android.synthetic.main.fragment_note_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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
        EventBus.getDefault().register(this)
        requestData()
        ElegantBus.getDefault("noteSaveState")
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(refreshNote: RefreshNote) {
        refreshData(refreshNote.type.id)
    }

}
