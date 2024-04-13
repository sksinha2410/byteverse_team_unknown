package com.sksinha2410.classmate.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sksinha2410.classmate.R

class SplashScreenActivity : AppCompatActivity()   {
    private lateinit var btnSignIn: TextView
    private lateinit var btnSignUp: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignIn.setOnClickListener{
            val mainIntent = Intent(this, SignInActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        btnSignUp.setOnClickListener{
            val mainIntent = Intent(this, SignUpActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Firebase.auth.currentUser
        if(FirebaseAuth.getInstance().currentUser?.isEmailVerified == true){
            if (currentUser != null) {
                var intent: Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Email not Verified",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}