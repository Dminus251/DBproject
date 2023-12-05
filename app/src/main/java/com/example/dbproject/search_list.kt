package com.example.dbproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class search_list : AppCompatActivity() {
    // 데이터베이스 참조 변수 선언
    private lateinit var database: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        // 리스트뷰 초기화
                listView = findViewById(R.id.listView)

        // 어댑터 초기화 (빈 데이터로 시작)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())

        // 어댑터를 리스트뷰에 연결
        listView.adapter = adapter

        // Firebase 데이터베이스 참조
        database = FirebaseDatabase.getInstance().reference

        // aroundMe 경로의 데이터 읽기
        readDataFromFirebase()
    }
    private fun readDataFromFirebase() {
        // search 경로에 대한 참조
        val searchReference: DatabaseReference = database.child("search")

        // 반복문을 통해 각 하위 경로의 데이터 읽기
        for (i in 0 until 10) {
            val childReference: DatabaseReference = searchReference.child(i.toString())

            // 데이터 변경 감지 리스너
            childReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // dataSnapshot에서 데이터를 가져와서 처리
                    if (dataSnapshot.exists()) {
                        val dataValue = dataSnapshot.value.toString()

                        // 데이터를 어댑터에 추가하고 갱신
                        adapter.add(dataValue)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.d("FirebaseData", "/search/$i 위치에 데이터가 없습니다.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("FirebaseData", "데이터 읽기 오류", databaseError.toException())
                }
            })
        }
    }
}