package com.sksinha2410.classmate.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sksinha2410.classmate.R

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    var permission_notif = false
    lateinit var permissions: Array<String>
    lateinit var notification: ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notification = findViewById(R.id.notification)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = arrayOf<String>(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        bottomNav = findViewById(R.id.bottom_nav_view)
        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_Home,R.id.fragment_update,R.id.fragment_book,R.id.fragment_resource))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNav.setupWithNavController(navController)
        if(!permission_notif){
            requestPermission();
        }
//        notification.setOnClickListener{
//            val intent  = Intent(this,NotificationActivity::class.java)
//            startActivity(intent)
//        }

    }


    private fun requestPermission() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    permissions.get(0)
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permission_notif = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(
                        applicationContext,
                        "Grant Permission for Latest Event Notification ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Grant Permission for Latest Event Notification ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                requestPermissionLauncherNotification.launch(permissions.get(0))
            }
        } catch (e: Exception) {
        }
    }

    private val requestPermissionLauncherNotification = registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permission_notif = true
        } else {
            permission_notif = false
            showPermissionDialog("Notification Permission")
        }
    }

    private fun showPermissionDialog(permission_desc: String) {
        AlertDialog.Builder(
            this@MainActivity
        ).setTitle("Alert for Notification Permission")
            .setPositiveButton("Settings", DialogInterface.OnClickListener { dialog, which ->
                val rintent = Intent()
                rintent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                rintent.setData(uri)
                startActivity(rintent)
                dialog.dismiss()
            }).setNegativeButton("Exit",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            .show()
    }
}