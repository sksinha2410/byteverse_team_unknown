package com.sksinha2410.classmate.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.classmate.DataClass.Books
import com.sksinha2410.classmate.R
import java.util.Calendar

class AddLabFiles : AppCompatActivity() {
    private lateinit var BookName: EditText
    private lateinit var submit: Button
    private lateinit var FileLink: EditText
    private lateinit var back: ImageView
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Subjects").child("ECE").child("4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lab_files)
        callByID()

        back.setOnClickListener{
            finish()
        }

        submit.setOnClickListener {
            var cTime = Calendar.getInstance().timeInMillis.toString()
            if (FileLink.text.isEmpty()) {
                Toast.makeText(this, "Please upload the file link", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (BookName.text.isEmpty()) {
                Toast.makeText(this, "Please enter the experiment number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val file: Books = Books()
                file.name= BookName.text.toString()
                file.pdfUrl = FileLink.text.toString()
                val itnt: Intent = intent
                val subjectName = itnt.getStringExtra("subjectName")!!
                dbRef.child(subjectName).child("labFiles").child(cTime).setValue(file)
                Toast.makeText(applicationContext,"File Uploaded", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    private fun callByID() {
        back = findViewById(R.id.ivBack)
        submit = findViewById(R.id.btnUpload)
        BookName = findViewById(R.id.exptno)
        FileLink = findViewById(R.id.fileLink)
    }
}