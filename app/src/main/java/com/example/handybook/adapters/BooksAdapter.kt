package com.example.handybook.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybook.R
import com.example.handybook.databinding.BookRomanItemBinding
import com.example.handybook.models.Book

class BooksAdapter(var list: List<Book>, val context: Context, val onClickBook: OnClickBook,):
    RecyclerView.Adapter<BooksAdapter.MyHolder>(){

    class MyHolder(binding: BookRomanItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val book = binding.item
        val image = binding.image
        val title = binding.bookName
        val author = binding.author
        val rating = binding.rating
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            BookRomanItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val book = list[position]
        holder.image.load(book.image)
        holder.title.text = book.name
        holder.author.text = book.author
        holder.rating.text = book.reyting.toDouble().toString()
        holder.book.setOnClickListener {
            onClickBook.onClickRoman(book)
        }
    }
    interface OnClickBook {
        fun onClickRoman(book: Book)
    }

}
