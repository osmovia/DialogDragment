package com.example.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter (private val mutableList: MutableList<CardData>,
                             private val owner: MainActivity?/*,
                             private val onItemClicked: (position: Int) -> Unit*/) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    

    class MyViewHolder(itemView: View/*, private val onItemClicked: (position: Int) -> Unit*/)
        : RecyclerView.ViewHolder(itemView)/*, View.OnClickListener*/{
        var textViewOriginal: TextView? = null
        var textViewTranslate: TextView? = null
        var itemContainer: ViewGroup? = null

        init {
            textViewOriginal = itemView.findViewById(R.id.textViewOriginal)
            textViewTranslate = itemView.findViewById(R.id.textViewTranslate)
            itemContainer = itemView.findViewById(R.id.itemContainer)
            /*itemView.setOnClickListener(this)*/
        }

        /*override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView/*, onItemClicked*/)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mutableList[position]
        holder.textViewOriginal?.text = mutableList[position].word
        holder.textViewTranslate?.text = mutableList[position].translate

        holder.itemContainer?.setOnClickListener {
            owner?.onItemClick(position)
        }
    }

    override fun getItemCount() = mutableList.size


}
