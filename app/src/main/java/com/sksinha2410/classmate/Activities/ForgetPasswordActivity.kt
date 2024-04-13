package com.sksinha2410.classmate.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.sksinha2410.classmate.R

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var btnReset: Button
    private lateinit var sEmail:String
    private lateinit var ivBack: ImageView
    private lateinit var autth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        email = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnSendLink)
        ivBack= findViewById(R.id.ivBack)
        autth = FirebaseAuth.getInstance()

        ivBack.setOnClickListener{
            val i = Intent(applicationContext,SignInActivity::class.java)
            startActivity(i)
            finish()
        }

        btnReset.setOnClickListener{
            sEmail = email.text.toString()

            autth.sendPasswordResetEmail(sEmail).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Email Sent Sucessfully", Toast.LENGTH_SHORT).show()
                    var i = Intent(this,SignInActivity::class.java)
                    i.putExtra("email",sEmail)
                    startActivity(i)
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Can't send email!!", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}