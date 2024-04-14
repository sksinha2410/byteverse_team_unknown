package com.sksinha2410.classmate.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.classmate.DataClass.Users
import com.sksinha2410.classmate.R

class SubjectDetailActivity : AppCompatActivity() {
    private lateinit var cvAssignment: CardView
    private lateinit var cvClassNotes: CardView
    private lateinit var cvLabFile: CardView
    private lateinit var subjectName: TextView
    private var deRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var add: ImageView
    private var userType:String=""
    private var admin:String=""
    private var uid:String= FirebaseAuth.getInstance().currentUser?.uid.toString()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_detail)
        cvClassNotes = findViewById(R.id.cvclassNotes)
        cvLabFile = findViewById(R.id.cvLabFiles)
        cvAssignment = findViewById(R.id.cvAssignment)
        subjectName = findViewById(R.id.subjectNames)
        add = findViewById(R.id.add)


        val itnt = intent
        var name = itnt.getStringExtra("subjectName")!!
        var url = itnt.getStringExtra("subjectUrl")!!

        FirebaseDatabase.getInstance().reference.child("Users").child(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("userType").exists()) {
                    userType = snapshot.child("userType").value.toString()
                    if(!userType.equals("0")){
                        add.visibility = View.VISIBLE
                    }else{
                        add.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        subjectName.text = name

        try {
            deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var us : Users
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

        }catch (e:Exception){}

        add.setOnClickListener{
            var selectedItemAtPosition: String = "ClassNotes"

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("What do you want to add?")
                .setPositiveButton("OK") { dialog, which ->
                    // Show a Toast with the selected item when the positive button is clicked
                    if(selectedItemAtPosition == "ClassNotes"){
                        val intent = Intent(applicationContext,AddClassNotesActivity::class.java)
                        intent.putExtra("subjectName",name)
                        intent.putExtra("type",selectedItemAtPosition)
                        startActivity(intent)
                    }

                    else if(selectedItemAtPosition == "Lab Files") {
                        val intent = Intent(applicationContext, AddLabFiles::class.java)
                        intent.putExtra("subjectName",name)
                        intent.putExtra("type",selectedItemAtPosition)
                        startActivity(intent)
                    }else if (selectedItemAtPosition == "Assignment"){
                        val intent = Intent(applicationContext, AddClassAssignment::class.java)
                        intent.putExtra("subjectName",name)
                        intent.putExtra("type",selectedItemAtPosition)
                        startActivity(intent)
                    }
                    // Do something.
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setSingleChoiceItems(
                    R.array.item_array, 0
                ) { dialog, which ->
                    // Store the selected item
                    val items = resources.getStringArray(R.array.item_array)
                    selectedItemAtPosition = items[which]
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        cvAssignment.setOnClickListener{
            val intent= Intent(this,ClassAssignmentActivity::class.java)
            intent.putExtra("subjectName",name)
            startActivity(intent)
        }

        cvClassNotes.setOnClickListener{
            val intent= Intent(this,PdfViewActivity::class.java)
            intent.putExtra("subjectName",name)
            startActivity(intent)
        }

        cvLabFile.setOnClickListener{
            val intent= Intent(this,LabFilesActivity::class.java)
            intent.putExtra("subjectName",name)
            startActivity(intent)
        }
    }
}