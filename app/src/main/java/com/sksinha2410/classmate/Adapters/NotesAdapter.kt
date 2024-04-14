package com.sksinha2410.classmate.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sksinha2410.classmate.DataClass.ClassNotes
import com.sksinha2410.classmate.R

class NotesAdapter(options: FirebaseRecyclerOptions<ClassNotes?>, s: String) :
    FirebaseRecyclerAdapter<ClassNotes?, NotesAdapter.userAdapterHolder?>(options) {
    lateinit var view: View
    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: ClassNotes
    ) {
        try {
            holder.bookCard.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.pdfUrl))
                ContextCompat.startActivity(holder.itemView.context, browserIntent, null)
            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.pdfUrl))
            ContextCompat.startActivity(holder.itemView.context, browserIntent, null)
        } catch (e: Exception) {
            Toast.makeText(holder.name.context, "Unable to open Notes", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.userAdapterHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.pdf_item, parent, false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo: ImageView
        var name: TextView
        var bookCard: CardView

        init {
            logo = itemView.findViewById<ImageView>(R.id.ivLogo)
            name = itemView.findViewById<TextView>(R.id.tv_title)
            bookCard = itemView.findViewById(R.id.bookCard)
        }
    }
}