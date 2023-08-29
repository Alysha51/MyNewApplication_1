package com.example.adminapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val categoryGrid = findViewById<GridLayout>(R.id.categoryGrid)


        // Set click listeners for each category
        categoryGrid.getChildAt(0).setOnClickListener { // Handle the click event for the "Update" category
            startActivity(Intent(this@CategoryActivity, UpdateActivity::class.java))
        }
        categoryGrid.getChildAt(1).setOnClickListener { // Handle the click event for the "Delete" category
            startActivity(Intent(this@CategoryActivity, DeleteActivity::class.java))
        }
        categoryGrid.getChildAt(2).setOnClickListener { // Handle the click event for the "Edit" category
            startActivity(Intent(this@CategoryActivity, UploadImageActivity::class.java))
        }
        categoryGrid.getChildAt(3).setOnClickListener { // Handle the click event for the "Upload Images" category
            startActivity(Intent(this@CategoryActivity, EditActivity::class.java))
        }
        categoryGrid.getChildAt(4).setOnClickListener { // Handle the click event for the "Add" category
            startActivity(Intent(this@CategoryActivity, AddActivity::class.java))
        }
        categoryGrid.getChildAt(5).setOnClickListener { // Handle the click event for the "Add" category
            startActivity(Intent(this@CategoryActivity, NotificationActivity::class.java))
        }
        // Add similar click listeners for other categories (Delete, Edit, Upload Image, Add)
    }

}
