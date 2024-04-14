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
import com.sksinha2410.classmate.DataClass.ClassFile
import com.sksinha2410.classmate.R

class AddClassNotesActivity : AppCompatActivity() {

    private lateinit var BookName: EditText
    private lateinit var FileLink: EditText
    private lateinit var submit: Button
    private lateinit var back: ImageView
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Subjects").child("ECE").child("4")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class_notes)
        callByID()

        var expNo = BookName.text.toString()

        back.setOnClickListener{
            finish()
        }

        submit.setOnClickListener {
            if (FileLink.text.isEmpty()) {
                Toast.makeText(this, "Please enter the file link", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val file: ClassFile = ClassFile()
                file.pdfUrl = FileLink.text.toString()
                file.Name= expNo

                val itnt: Intent = intent
                val subjectName = itnt.getStringExtra("subjectName")!!

                dbRef.child(subjectName).child("Notes").child("1").setValue(file)
                Toast.makeText(applicationContext,"File Uploaded", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    private fun callByID() {
        submit = findViewById(R.id.btnUpload)
        BookName = findViewById(R.id.etName)
        FileLink = findViewById(R.id.fileLink)
        back = findViewById(R.id.ivBack)
    }
}