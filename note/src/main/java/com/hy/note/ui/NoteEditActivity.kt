package com.hy.note.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cody.bus.ElegantBus
import cody.bus.ElegantLog
import cody.bus.ObserverWrapper
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.common.base.BaseActivity
import com.hy.common.model.DicType
import com.hy.common.navigator.NoteNavigator
import com.hy.note.R
import com.hy.note.databinding.ActivityNoteEditBinding
import com.hy.note.widget.DicPopupWin
import com.hy.utils.TimeUtil
import kotlinx.android.synthetic.main.activity_note_edit.*

@Route(path = NoteNavigator.EDIT_PAGE_PATH)
class NoteEditActivity : BaseActivity<ActivityNoteEditBinding>() {
    lateinit var noteEditViewModel: NoteEditViewModel
    lateinit var toolbar: Toolbar;
    var dicType: DicType? = null;
    var noteId :Int ?=null
    override fun initView(){
        toolbar = findViewById(R.id.toolbar_note)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener({
             finish()
        })

        noteEditViewModel = ViewModelProvider(this).get(NoteEditViewModel::class.java)
        binding.run {
            viewModel = noteEditViewModel
            dicSelectTv.setOnClickListener {
                val dicPopuWin = DicPopupWin(0,this@NoteEditActivity){
                    dicType = it
                    dicType?.let {
                        noteEditViewModel.type.value = it.id
                    }

                }
                dicPopuWin.show(it)
            }
            editContent.setFocusable(true)
            editContent.setFocusableInTouchMode(true)
            editContent.requestFocus()
        }


    }

    override fun initData(){
        noteId = intent.getIntExtra(NoteNavigator.NOTE_ID,0)
        noteEditViewModel.noteId = noteId
        noteEditViewModel.saveSuccess.observe(this, Observer {
            if(it){
                Toast.makeText(getApplication(),"保存成功",Toast.LENGTH_SHORT).show()
                ElegantBus.getDefault("saveState").post(true);
            }else{
                Toast.makeText(getApplication(),"保存失败",Toast.LENGTH_SHORT).show()
                ElegantBus.getDefault("saveState").post(false);
            }
            //收起软键盘
            val imm = this@NoteEditActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            imm.hideSoftInputFromWindow(this@NoteEditActivity.getWindow().getDecorView().getWindowToken(), 0);
        })
        if(noteId!=0){
            noteEditViewModel.getNote(0,noteId!!)
        }
    }

    override fun onCreateLayout(): Int {
        return R.layout.activity_note_edit
    }


}