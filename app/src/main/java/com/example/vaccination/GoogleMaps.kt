package com.example.vaccination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.vaccination.databinding.ActivityGoogleMapsBinding
import com.google.android.gms.maps.model.CameraPosition

class GoogleMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        val seodaemun = LatLng(37.57857924443986, 126.93624973297119)

        val marker = MarkerOptions()
            .position(seodaemun)
            .title("서대문구 보건소")
        //마커
        mMap.addMarker(marker)

        val cameraOption = CameraPosition.builder()
            .target(seodaemun)
            .zoom(12f)
            .build()

        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        //카메라의 위치
        mMap.moveCamera(camera)


        //마포
        val mapo = LatLng(37.5663123, 126.9020798)

        val marker1 = MarkerOptions()
            .position(mapo)
            .title("마포구 보건소")
        //마커
        mMap.addMarker(marker1)

        val cameraOption1 = CameraPosition.builder()
            .target(mapo)
            .zoom(12f)
            .build()

        val camera1 = CameraUpdateFactory.newCameraPosition(cameraOption1)

        //카메라의 위치
        mMap.moveCamera(camera1)


        //용산구
        val yongsan = LatLng(37.5323504, 126.9903613)

        val marker2 = MarkerOptions()
            .position(yongsan)
            .title("용산구 보건소")
        //마커
        mMap.addMarker(marker2)

        val cameraOption2 = CameraPosition.builder()
            .target(yongsan)
            .zoom(12f)
            .build()

        val camera2 = CameraUpdateFactory.newCameraPosition(cameraOption2)

        //카메라의 위치
        mMap.moveCamera(camera2)



        //종로구
        val jongno = LatLng(37.5817685, 126.9692571)

        val marker3 = MarkerOptions()
            .position(jongno)
            .title("종로구 보건소")
        //마커
        mMap.addMarker(marker3)

        val cameraOption3 = CameraPosition.builder()
            .target(jongno)
            .zoom(12f)
            .build()

        val camera3 = CameraUpdateFactory.newCameraPosition(cameraOption3)

        //카메라의 위치
        mMap.moveCamera(camera3)



        //성동구
        val seongdong = LatLng(37.5674274, 127.0329852)

        val marker4 = MarkerOptions()
            .position(seongdong)
            .title("성동구 보건소")
        //마커
        mMap.addMarker(marker4)

        val cameraOption4 = CameraPosition.builder()
            .target(seongdong)
            .zoom(12f)
            .build()

        val camera4 = CameraUpdateFactory.newCameraPosition(cameraOption4)

        //카메라의 위치
        mMap.moveCamera(camera4)


        //동작구
        val dongak = LatLng(37.504569, 126.940919)

        val marker5 = MarkerOptions()
            .position(dongak)
            .title("동작구 보건소")
        //마커
        mMap.addMarker(marker5)

        val cameraOption5 = CameraPosition.builder()
            .target(dongak)
            .zoom(12f)
            .build()

        val camera5 = CameraUpdateFactory.newCameraPosition(cameraOption5)

        //카메라의 위치
        mMap.moveCamera(camera5)


        //강남구
        val gangnam = LatLng(37.516283, 127.0422773)

        val marker6 = MarkerOptions()
            .position(gangnam)
            .title("강남구 보건소")
        //마커
        mMap.addMarker(marker6)

        val cameraOption6 = CameraPosition.builder()
            .target(gangnam)
            .zoom(12f)
            .build()

        val camera6 = CameraUpdateFactory.newCameraPosition(cameraOption6)

        //카메라의 위치
        mMap.moveCamera(camera6)


        //서초구
        val seocho = LatLng(37.4836015, 127.0334387)

        val marker7 = MarkerOptions()
            .position(seocho)
            .title("서초구 보건소")
        //마커
        mMap.addMarker(marker7)

        val cameraOption7 = CameraPosition.builder()
            .target(seocho)
            .zoom(12f)
            .build()

        val camera7 = CameraUpdateFactory.newCameraPosition(cameraOption7)

        //카메라의 위치
        mMap.moveCamera(camera7)




    }
}