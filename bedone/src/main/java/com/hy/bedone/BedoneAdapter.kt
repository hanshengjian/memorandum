package com.hy.bedone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hy.bedone.databinding.ItemBedoneBinding
import com.hy.common.model.Bedone

class BedoneAdapter : RecyclerView.Adapter<BedoneAdapter.ViewHolder>() {
    var bedones: MutableList<Bedone>? = null

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var viewDataBinding: ItemBedoneBinding;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ItemBedoneBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_bedone,
            parent, false
        )
        return ViewHolder(dataBinding.root).apply {
            viewDataBinding = dataBinding
        }
    }

    override fun getItemCount(): Int {
        return bedones?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bedone = bedones!![position]
        holder.viewDataBinding.bedoneTv.text = bedone.content
    }

    fun clearData() {
        bedones?.clear()
    }

}