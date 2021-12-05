package com.hy.note.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hy.utils.TimeUtil

public class NoteBindingAdapter {

    companion object{
        @BindingAdapter("app:textTime")
        @JvmStatic
        fun setTime(textView: TextView,timeStamp:Long){
            val timeStr = TimeUtil.toDateyyyy_MM_DD(timeStamp)
            textView.text = timeStr
        }
    }


}