package com.sksinha2410.classmate.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.classmate.Activities.AddUpdateActivity
import com.sksinha2410.classmate.Adapters.UpdateAdapter
import com.sksinha2410.classmate.DataClass.Updates
import com.sksinha2410.classmate.R

class UpdatesFragment : Fragment() {
    private lateinit var rvUpdate: RecyclerView
    private lateinit var ppAdapter: UpdateAdapter
    var deRef = FirebaseDatabase.getInstance().getReference("Updates")
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(R.layout.fragment_updates, container, false)
        val addUpdate : FloatingActionButton = view.findViewById(R.id.btnAddUpdate)
        rvUpdate = view.findViewById(R.id.recUpdate)
        addUpdate.setOnClickListener{
            val intent: Intent = Intent(activity, AddUpdateActivity::class.java)
            startActivity(intent)
        }
        rvUpdate.itemAnimator = null
        val options: FirebaseRecyclerOptions<Updates?> = FirebaseRecyclerOptions.Builder<Updates>().
        setQuery(deRef, Updates::class.java).build()
        ppAdapter = UpdateAdapter(options)
        rvUpdate.adapter = ppAdapter
        ppAdapter.startListening()
        return view
    }
}