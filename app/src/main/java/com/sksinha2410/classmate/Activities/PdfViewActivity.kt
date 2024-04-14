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
import com.sksinha2410.classmate.Adapters.NotesAdapter
import com.sksinha2410.classmate.DataClass.ClassNotes
import com.sksinha2410.classmate.R

class PdfViewActivity : AppCompatActivity() {
    private lateinit var rep_recycler: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var labFilesAdapter: NotesAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        progress = findViewById(R.id.progressBar)

        rep_recycler= findViewById(R.id.Notes_Recycler)
        val itnt: Intent = intent
        val subjectName = itnt.getStringExtra("subjectName")!!
        rep_recycler.itemAnimator = null

        val options: FirebaseRecyclerOptions<ClassNotes?> =
            FirebaseRecyclerOptions.Builder<ClassNotes>()
                .setQuery(
                    FirebaseDatabase.getInstance().reference.child("Subjects").child("ECE").child("4").
                    child(subjectName).child("Notes"),
                    ClassNotes::class.java
                )
                .build()
        labFilesAdapter = NotesAdapter(options, "Class Notes")
        rep_recycler.adapter = labFilesAdapter
        labFilesAdapter.startListening()
        progress.visibility = View.INVISIBLE
    }
}