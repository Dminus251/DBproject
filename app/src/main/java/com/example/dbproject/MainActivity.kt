package com.example.dbproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aroundBtn = findViewById<Button>(R.id.aroundMe)
        aroundBtn.setOnClickListener {
            var intent1 = Intent(this, around::class.java)
            startActivity(intent1)
        }
        val searchBtn = findViewById<Button>(R.id.search)
        searchBtn.setOnClickListener {
            var intent2 = Intent(this, search::class.java)
            startActivity(intent2)
        }
    }
}