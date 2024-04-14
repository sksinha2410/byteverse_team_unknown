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
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sksinha2410.classmate.DataClass.Books
import com.sksinha2410.classmate.R
class LabFilesAdapter(options: FirebaseRecyclerOptions<Books?>, val clubName: String) :
    FirebaseRecyclerAdapter<Books?, LabFilesAdapter.userAdapterHolder?>(options) {
    lateinit var view: View
    override fun onBindViewHolder(holder: userAdapterHolder,
                                  position: Int,
                                  model: Books) {
        try {
            holder.name.text = model.name

            Glide.with(holder.logo.context).load(model.bookUrl).into(holder.logo)

            holder.bookCard.setOnClickListener {
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.pdfUrl))
                    ContextCompat.startActivity(holder.itemView.context, browserIntent, null)
                } catch (e: Exception) {
                    Toast.makeText(holder.name.context, "File Link not found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(holder.name.context, "Something wrong in Developer", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapterHolder {
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