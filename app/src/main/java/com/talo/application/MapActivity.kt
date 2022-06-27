package com.talo.application

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback{

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 0){
            for (result in grantResults)
                if (result != PackageManager.PERMISSION_GRANTED)
                    finish()
            loadMap()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        loadMap()
    }

    override fun onMapReady(maps: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED
        ) {
         ActivityCompat.requestPermissions(this,
             arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else{
            maps.isMyLocationEnabled = true
            val  marker = MarkerOptions()
            marker.position(LatLng(25.33611, 121.56500))
            marker.title("Taipei 101")
            marker.draggable(true)
            maps.addMarker(marker)
            marker.position(LatLng(25.047924, 121.517081))
            marker.title("Taipei station")
            marker.draggable(true)
            maps.addMarker(marker)
            val polylineOpt = PolylineOptions()
            polylineOpt.add(LatLng(25.033611, 121.565000))
            polylineOpt.add(LatLng(25.032435, 121.534905))
            polylineOpt.add(LatLng(25.047924, 121.517081))
            polylineOpt.color(Color.GREEN)
            val polyline = maps.addPolyline(polylineOpt)
            polyline.width = 12f
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.035, 121.54), 13f))
        }
}
    private fun loadMap() {
        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)

    }
}