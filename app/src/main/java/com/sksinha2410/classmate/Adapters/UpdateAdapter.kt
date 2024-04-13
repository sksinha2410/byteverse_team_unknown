package com.sksinha2410.classmate.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.classmate.DataClass.Updates
import com.sksinha2410.classmate.R

class UpdateAdapter (options: FirebaseRecyclerOptions<Updates?>) :
    FirebaseRecyclerAdapter<Updates?, UpdateAdapter.userAdapterHolder?>(options) {
    val dRef = FirebaseDatabase.getInstance().getReference("Updates")
    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: Updates
    ) {

        holder.title.text = model.title
        holder.description.text = model.description
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.update_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView: View): RecyclerView.ViewHolder(innerView) {
        var description: TextView
        var title: TextView

        init {
            title =innerView.findViewById(R.id.tvTitle)
            description =innerView.findViewById(R.id.tvDesc)
        }
    }
}