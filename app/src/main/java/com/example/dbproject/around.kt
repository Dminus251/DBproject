package com.example.dbproject

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database

class around : AppCompatActivity() {

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_around)

        val gpsBtn = findViewById<Button>(R.id.gpsBtn)
        val goToAround = findViewById<Button>(R.id.goToAroundListBtn)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // gpsBtn을 누르면 위치 권한 허용 메시지를 띄움
        gpsBtn.setOnClickListener {
            checkLocationPermissionAndRequest()
        }

        //문화축제 목록 보기를 누르면 해당 레이아웃으로 이동
        goToAround.setOnClickListener {
            var intent = Intent(this, aroundList::class.java)
            startActivity(intent)
        }
    }

    private fun checkLocationPermissionAndRequest() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 이미 허용된 경우
            requestLocationUpdates()
        } else {
            // 권한을 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        latitude = location.latitude
                        longitude = location.longitude

                        
                        val latitude_tx = findViewById<TextView>(R.id.myLatitude)
                        val langitude_tx = findViewById<TextView>(R.id.mylongitude)

                        latitude_tx.text="${latitude}"
                        langitude_tx.text = "${longitude}"

                        //파이어베이스에 데이터 작성
                        val database = Firebase.database
                        val latitudeRef = database.getReference("latitude")
                        latitudeRef.setValue("${latitude}")

                        val longitudeRef = database.getReference("longitude")
                        longitudeRef.setValue("${longitude}")
                    }
                }
            },
            null
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 사용자가 권한을 허용한 경우
                    requestLocationUpdates()
                } else {
                    // 사용자가 권한을 거부한 경우
                    showToast("위치 권한이 필요합니다.")
                }
            }
        }
    }
}
