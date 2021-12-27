package com.hy.note.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.utils.TimeUtil

public class NoteBindingAdapter {

    companion object{
        @BindingAdapter("app:textTime")
        @JvmStatic
        fun setTime(textView: TextView,timeStamp:Long){
            val timeStr = TimeUtil.toDateyyyy_MM_DD(timeStamp)
            textView.text = timeStr
        }

        @BindingAdapter("app:NoteType")
        @JvmStatic
        fun setNoteType(textView: TextView,type:Int){
            if(type == 0){
                textView.setText("未分类")
            }else{
                NavigatorManager.getNavigator(DicManagerNavigator::class.java)?.getDicManager()
                    ?.getDicType(type) { dicType, s ->
                        textView.text = dicType?.content
                        NoteMemCache.dicType = dicType
                    }
            }
        }
    }


}