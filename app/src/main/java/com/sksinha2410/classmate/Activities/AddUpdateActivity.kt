package com.sksinha2410.classmate.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.classmate.DataClass.Updates
import com.sksinha2410.classmate.R
import java.util.Calendar

class AddUpdateActivity : AppCompatActivity() {
     private lateinit var update: EditText
        private lateinit var submit: Button
        private lateinit var tvDesc: EditText
        private lateinit var back: ImageView
        private  var deRef = FirebaseDatabase.getInstance().getReference("Updates")


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_add_update)
            callByID()

            back.setOnClickListener{
                finish()
            }

            submit.setOnClickListener {
                val cTime = Calendar.getInstance().timeInMillis.toString()
                if (tvDesc.text.isEmpty()) {
                    Toast.makeText(this, "Please upload the Description", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (update.text.isEmpty()) {
                    Toast.makeText(this, "Please enter the title", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }else{
                    val file: Updates = Updates()
                    file.title = update.text.toString()
                    file.description = tvDesc.text.toString()
                    deRef.child(cTime).setValue(file)
                    Toast.makeText(applicationContext,"Update Sent", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        private fun callByID() {
            back = findViewById(R.id.ivBack)
            submit = findViewById(R.id.btnUpload)
            update = findViewById(R.id.update)
            tvDesc = findViewById(R.id.tvDesc)
        }
    }