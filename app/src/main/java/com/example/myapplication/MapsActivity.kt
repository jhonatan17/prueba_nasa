package com.example.myapplication

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
   // private lateinit var marker:Marker
   lateinit var marker:Marker
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation:Location
    private lateinit var mMap: GoogleMap

    companion object{
        private  const val LOCATION_PERMISSION_REQUEST_CODE=1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        buttonm.setOnClickListener(View.OnClickListener { setUpMap() })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        mMap.uiSettings.isZoomControlsEnabled =true
setUpMap()
        // Add a marker in Sydney and move the camera

    }

    private fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this,
           android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        //mMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener (this ) {location->
Toast.makeText(this,""+location.latitude +" y "+location.longitude,Toast.LENGTH_SHORT).show();
            if(location!=null){
                lastLocation =location
                val currentLatLong=LatLng(location.latitude,location.longitude)

              //  if(marker.isVisible==null)
                marker=mMap.addMarker(MarkerOptions().position(currentLatLong).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)).rotation(45f).draggable(true));
                Toast.makeText(this,marker.id,Toast.LENGTH_SHORT).show();
             /*   mMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener (){
                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
                    true})*/
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,13f))
                marker.rotation+=95f
            }
        }
    }
}
