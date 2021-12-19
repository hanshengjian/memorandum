package com.hy.common.widget

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.hy.common.R
import com.hy.common.model.DicType

class DicPopAdapter(var dicTypes:List<DicType> ?= null) : RecyclerView.Adapter<DicPopAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dic_popup_item,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dicType = dicTypes?.get(position)
        dicType?.let {
            holder.mDicNameTv?.text = it.content
        }
    }

    override fun getItemCount(): Int {
        return dicTypes?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var mDicNameTv:TextView ?=null
        init {
            mDicNameTv = itemView.findViewById(R.id.dic_name)
        }
    }
}