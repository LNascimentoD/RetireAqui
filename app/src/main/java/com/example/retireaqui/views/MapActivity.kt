package com.example.retireaqui.views

import android.Manifest
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import com.example.retireaqui.R
import com.example.retireaqui.network.services.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest.permission

import androidx.core.app.ActivityCompat

import com.google.android.gms.tasks.OnCompleteListener

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    val locationService = LocationService()

    lateinit var name: String
    lateinit var lat: String
    lateinit var long: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        name = intent.getStringExtra("name").toString()
        lat = intent.getStringExtra("lat").toString()
        long = intent.getStringExtra("long").toString()

        var actionBar = getSupportActionBar()

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        if(lat != ""){
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat.toDouble(), long.toDouble()))
                    .title(name))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat.toDouble(), long.toDouble())))
        }else{
            locationService.getAllLocation() { allLocation ->
                this.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location !== null) {
                        for(mark in allLocation){
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(mark.lat.toDouble(), mark.long.toDouble()))
                                    .title(mark.name))
                        }

                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(location.latitude, location.longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .title("Localização atual"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
                    }
                }.addOnFailureListener { error ->

                }
            }
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