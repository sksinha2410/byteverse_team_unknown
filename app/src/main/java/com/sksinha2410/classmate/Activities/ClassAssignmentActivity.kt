package com.sksinha2410.classmate.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.classmate.Adapters.AssignmentAdapter
import com.sksinha2410.classmate.DataClass.Books
import com.sksinha2410.classmate.R

class ClassAssignmentActivity : AppCompatActivity() {
    private lateinit var rep_recycler: RecyclerView
    private lateinit var labFilesAdapter: AssignmentAdapter
    private lateinit var progress: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_assignment)
        progress = findViewById(R.id.progressBar)

        rep_recycler= findViewById(R.id.lab_Recycler)
        val itnt: Intent = intent
        val subjectName = itnt.getStringExtra("subjectName")!!
        rep_recycler.itemAnimator = null

        val options: FirebaseRecyclerOptions<Books?> =
            FirebaseRecyclerOptions.Builder<Books>()
                .setQuery(
                    FirebaseDatabase.getInstance().reference.child("Subjects").child("ECE").child("4").
                    child(subjectName).child("assignment"),
                    Books::class.java
                ).build()

        labFilesAdapter = AssignmentAdapter(options, "assignment")
        rep_recycler.adapter = labFilesAdapter
        labFilesAdapter.startListening()
        progress.visibility = View.GONE
    }
}