package com.example.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter (private var mutableList: MutableList<CardData>,
                             private val owner: MainActivity?) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textViewOriginal: TextView? = null
        var textViewTranslate: TextView? = null
        var itemContainer: ViewGroup? = null

        init {
            textViewOriginal = itemView.findViewById(R.id.textViewOriginal)
            textViewTranslate = itemView.findViewById(R.id.textViewTranslate)
            itemContainer = itemView.findViewById(R.id.itemContainer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mutableList[position]
        holder.textViewOriginal?.text = item.word
        holder.textViewTranslate?.text = item.translate

        holder.itemContainer?.setOnClickListener {
            owner?.onItemClick(item)
        }
    }

    override fun getItemCount() = mutableList.size

    fun setWords(item: CardData) {
        mutableList.add(item)
        notifyDataSetChanged()
    }

    fun setWord(newCardData: CardData) {
        mutableList.forEachIndexed { index, cardData ->
            if (cardData.id == newCardData.id) {
                mutableList[index] = newCardData
                notifyItemChanged(index)
                return
            }
        }
    }
}