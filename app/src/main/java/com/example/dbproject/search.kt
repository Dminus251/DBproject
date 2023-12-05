package com.example.dbproject
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference


class search : AppCompatActivity() {

    // Firebase 데이터베이스 참조 변수 선언
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btn = findViewById<Button>(R.id.searchBtn)
        btn.setOnClickListener {
            val intent = Intent(this, search_list::class.java)
            startActivity(intent)
        }
        }

    }

