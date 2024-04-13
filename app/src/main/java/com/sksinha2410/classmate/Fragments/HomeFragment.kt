package com.sksinha2410.classmate.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.classmate.Adapters.SubjectsAdapter
import com.sksinha2410.classmate.DataClass.Subjects
import com.sksinha2410.classmate.R

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var subjectsAdapter: SubjectsAdapter
    private lateinit var progress: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.subject_Recycler)
        progress = view.findViewById(R.id.progressBar)
        recyclerView.itemAnimator = null

        val options: FirebaseRecyclerOptions<Subjects?> =
            FirebaseRecyclerOptions.Builder<Subjects>()
                .setQuery(
                    FirebaseDatabase.getInstance().reference.child("Subjects").child("ECE").child("4"),
                    Subjects::class.java
                )
                .build()
        subjectsAdapter = SubjectsAdapter(options)
        recyclerView.adapter = subjectsAdapter
        subjectsAdapter.startListening()

        subjectsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()

                // Now you can reliably get the total item count
                val itemCount = subjectsAdapter.itemCount
                println("ItemCount: Total items = $itemCount")

                // Display the item count in a Toast
                Toast.makeText(view.context.applicationContext, itemCount.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        progress.visibility = View.INVISIBLE
        return view
    }
}