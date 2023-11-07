package com.example.handybook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybook.R
import com.example.handybook.models.Book

class BookCabinetAdapter(var kitobList:MutableList<Book>) : RecyclerView.Adapter<BookCabinetAdapter.CartHolder>(){

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.name)
        var author: TextView = itemView.findViewById(R.id.author)
        var rank: TextView = itemView.findViewById(R.id.rank)
        var imageView:ImageView=itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        return CartHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_roman_item, parent, false))
    }

    override fun getItemCount(): Int {
        return kitobList.size
    }

    override fun onBindViewHolder(holder:CartHolder, position: Int) {
        var kitob = kitobList[position]
        holder.name.text = kitob.name
        holder.author.text=kitob.author
        holder.imageView.load(kitob.image)
        holder.rank.text=kitob.reyting.toString()

    }

}