package com.sksinha2410.classmate.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sksinha2410.classmate.DataClass.Books
import com.sksinha2410.classmate.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Calendar
import java.util.UUID

class AddBookActivity : AppCompatActivity() {
    private lateinit var BookName: EditText
    private lateinit var etBookUrl: EditText
    private lateinit var submit: Button
    private lateinit var uploadImg: ImageView
    private lateinit var back: ImageView
    private var storageReference = FirebaseStorage.getInstance().reference
    val Pick_image=1
    private var BookUrl:String = ""
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Books")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        callByID()

        uploadImg.setOnClickListener{
            openGallery()
        }

        back.setOnClickListener{
            finish()
        }

        submit.setOnClickListener {
            val cTime = Calendar.getInstance().timeInMillis.toString()
            if (BookName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter the Book name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (etBookUrl.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter the Book Url", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (BookUrl.isEmpty()){
                Toast.makeText(this, "Please upload the Book image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val file: Books = Books(
                    bookUrl = BookUrl,
                    pdfUrl = etBookUrl.text.toString(),
                    name = BookName.text.toString()
                )

                dbRef.child(cTime).setValue(file)
                Toast.makeText(applicationContext,"File Uploaded", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    private fun openGallery() {
        val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery,Pick_image)
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Pick_image && resultCode == RESULT_OK && data != null) {
            val resultUri: Uri = data.data!!
            uploadImageToFirebase(resultUri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileRef: StorageReference =
            storageReference.child("image/Books/${UUID.randomUUID()}.jpg")

        // Load the image into a Bitmap
        val bitmap: Bitmap
        try {
            // Assuming imageUri is a valid URI
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            bitmap = ImageDecoder.decodeBitmap(source)
            // Use the bitmap here...
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

        // Compress the image with reduced quality (adjust quality as needed)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos) // Adjust the quality here (50 in this example)

        // Convert the compressed Bitmap to bytes
        val data = baos.toByteArray()

        // Upload the compressed image to Firebase Storage
        val uploadTask = fileRef.putBytes(data)
        uploadTask.addOnSuccessListener { // Handle the successful upload
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                BookUrl = uri.toString()

                Glide.with(applicationContext).load(BookUrl).into(uploadImg)
            }
        }.addOnFailureListener { // Handle the failure to upload
            Toast.makeText(applicationContext, "Failed.", Toast.LENGTH_LONG).show()
        }
    }

    private fun callByID() {
        etBookUrl = findViewById(R.id.etBookUrl)
        uploadImg = findViewById(R.id.uploadIMG)
        uploadImg = findViewById(R.id.uploadIMG)
        BookName = findViewById(R.id.etBookName)
        submit = findViewById(R.id.btnUpload)
        back= findViewById(R.id.ivBack)
    }
}