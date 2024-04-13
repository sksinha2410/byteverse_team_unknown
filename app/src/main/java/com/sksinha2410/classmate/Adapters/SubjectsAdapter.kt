package com.sksinha2410.classmate.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sksinha2410.classmate.Activities.SubjectDetailActivity
import com.sksinha2410.classmate.DataClass.Subjects
import com.sksinha2410.classmate.R

class SubjectsAdapter(options: FirebaseRecyclerOptions<Subjects?>) :
    FirebaseRecyclerAdapter<Subjects?, SubjectsAdapter.userAdapterHolder?>(options) {
    lateinit var view: View
    override fun onBindViewHolder(holder: userAdapterHolder,
                                  position: Int,
                                  model: Subjects) {
        try {
            holder.name.text = model.subject_name

            Glide.with(holder.logo.context).load(model.imageUrl).into(holder.logo)
            holder.subjectCard.setOnClickListener{
                val intent = Intent(view.context, SubjectDetailActivity::class.java)
                intent.putExtra("subjectName", model.subject_name)
                intent.putExtra("subjectUrl", model.imageUrl)
                try {
                    intent.putExtra("eventpic1",model.eventpic1)
                    intent.putExtra("eventpic2",model.eventpic2)
                }catch (e:Exception){}

                // Pass any necessary data to the new activity
                holder.itemView.context.startActivity(intent)

            }
        } catch (e: Exception) {
            Toast.makeText(holder.name.context, "Something wrong in Developer", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapterHolder {
        view =LayoutInflater.from(parent.context).inflate(R.layout.subject_item, parent, false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo: ImageView
        var name: TextView
        var subjectCard: CardView
        init {
            logo = itemView.findViewById<ImageView>(R.id.ivLogo)
            name = itemView.findViewById<TextView>(R.id.tv_title)
            subjectCard = itemView.findViewById(R.id.subjectCard)
        }
    }
}