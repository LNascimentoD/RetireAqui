package com.example.retireaqui.views.manager_context

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.retireaqui.R
import com.example.retireaqui.network.services.ShopService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateShopActivity : AppCompatActivity(), OnMapReadyCallback {
    var auth = Firebase.auth
    val shopService = ShopService()

    lateinit var email: String

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var lat: Double = 0.0
    var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_create_shop)
        email = auth.currentUser?.email.toString()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var actionBar = getSupportActionBar()

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_shop) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onClickCreate()
    }

    private fun onClickCreate(){
        val editName: EditText = findViewById(R.id.create_place_name_value)

        val place_name = editName.getText()

        val btnOnCreate = findViewById<Button>(R.id.create_place_button)
        btnOnCreate.setOnClickListener {
            shopService.createPlace(place_name.toString(), email, lat.toString(), long.toString()) { result ->
                if(result){
                    val activityListShop = Intent(this, ListShopActivity::class.java)
                    startActivity(activityListShop)
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Não foi possível persistir os dados do estabelecimento",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        this.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location !== null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
            }
        }.addOnFailureListener { error ->

        }

        mMap.setOnMapClickListener { result ->
            lat = result.latitude
            long = result.longitude

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, long))
                    .title("Location"))
        }
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }
}