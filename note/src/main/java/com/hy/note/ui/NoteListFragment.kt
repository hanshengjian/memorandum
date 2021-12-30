package com.hy.note.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.hy.common.base.BaseFragment
import com.hy.common.model.Note
import com.hy.common.widget.DicPopupWin
import com.hy.note.R
import com.hy.note.databinding.FragmentNoteListBinding
import kotlinx.android.synthetic.main.fragment_note_list.*


/**
 * @Author Lenovo
 */
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {
    lateinit var noteListiewModel: NoteListViewModel;
    var dicPopupWindow:DicPopupWin?=null
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

        dic_note_ll.setOnClickListener {
           // if(dicPopupWindow==null){
            dic_arrow_iv.setImageResource(R.mipmap.arrow_down_bold)
            dicPopupWindow = DicPopupWin(0, activity) { type ->
                //重新刷新数据
                refreshData(type)
            }
            dicPopupWindow!!.setOnDismissListener {
                dic_arrow_iv.setImageResource(R.mipmap.arrow_up_bold)
            }
            dicPopupWindow!!.show(it)
        }

        noteListAdapter.menuItemListener = object : NoteListAdapter.onMenuItemListener {
            override fun deleteItem(position: Int, note: Note?) {

            }

            override fun toListOfTop() {
                //todo
            }

            override fun moteToOtherDic() {
                //todo
            }

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
        refreshData(type = -1)
    }

    fun refreshData(type:Int?){
        if (type != null) {
            noteListiewModel.getNotes(type)?.observe(viewLifecycleOwner, Observer {
                noteListAdapter.apply {
                    notes = it
                    if(it.isNotEmpty()){
                        ll_empty.visibility = View.GONE
                        notifyDataSetChanged()
                    }
                }
            })
        }
    }

}
