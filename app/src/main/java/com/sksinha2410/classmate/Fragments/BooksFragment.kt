package com.sksinha2410.classmate.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.classmate.Activities.AddBookActivity
import com.sksinha2410.classmate.Adapters.BookAdapter
import com.sksinha2410.classmate.DataClass.Books
import com.sksinha2410.classmate.DataClass.Users
import com.sksinha2410.classmate.R

class BooksFragment : Fragment() {
    private var deRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var add: ImageView
    private lateinit var progress: ProgressBar
    private var admin: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        recyclerView = view.findViewById(R.id.book_Recycler)
        progress = view.findViewById(R.id.progressBar)
        add = view.findViewById(R.id.add)

        try {
            deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val us : Users
                    if(snapshot.exists()){
                        us = snapshot.getValue(Users::class.java)!!
                        admin = us.userType
                        if (admin == "0"){
                            add.visibility = View.GONE
                        }else if(admin =="1"){
                            add.visibility = View.VISIBLE
                        }
                        else{
                            add.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors here
                    Log.e("Firebase", "Error fetching events: ${error.message}")
                }
            })

        }catch (e:Exception){
            Toast.makeText(view.context.applicationContext, e.message, Toast.LENGTH_SHORT).show()
        }

        recyclerView.itemAnimator = null

        val options: FirebaseRecyclerOptions<Books?> =
            FirebaseRecyclerOptions.Builder<Books>()
                .setQuery(
                    FirebaseDatabase.getInstance().reference.child("Books"),
                    Books::class.java
                )
                .build()
        bookAdapter = BookAdapter(options)
        recyclerView.adapter = bookAdapter
        bookAdapter.startListening()

        bookAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()

                // Now you can reliably get the total item count
                val itemCount = bookAdapter.itemCount
                println("ItemCount: Total items = $itemCount")

                // Display the item count in a Toast
                Toast.makeText(
                    view.context.applicationContext,
                    itemCount.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        add.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
            builder

                .setTitle("Do You want to add book?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(
                        activity, AddBookActivity::class.java
                    )
                    startActivity(intent)
                }
                .setNegativeButton("No") { _, _ ->
                    // Do something else.
                }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        progress.visibility = View.INVISIBLE
        return view
    }
}