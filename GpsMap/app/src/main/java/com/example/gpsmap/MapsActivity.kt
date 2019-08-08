package com.example.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //위치 정보를 주기적으로 얻는 데 필요한 객체들을 선언한다. -1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    
    //PolyLine옵션
    //>먼저 PolylineOptions() 객체를 생성. 
    //>선을 이루는 좌표들과 선의 굵기, 색상등을 설정.굵기 5f, 색상은 빨강
    private val polylineOptions = PolylineOptions().width(5f).color(Color.RED)

    private val REQUEST_ACCESS_FINE_LOCATION = 1000




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받는다.
        //> 프래그먼트 매니저로부터 SupportMapFragment를 얻는다.
        //> getMapAsync() 메서드로 지도가 준비되면 알림을 받는다.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //1에서 선언한 변수들을 onCreate() 메서드의 끝에서 초기화한다.
        locationInit()

    }//onCreate


    //위치 정보를 얻기 위한 각종 초기화
    //LocationRequest는 위치 정보 요청에 대한 세부 정보를 설정한다.
    private fun locationInit(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallback()

        locationRequest = LocationRequest()
        //GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //업데이트 인터벌
        //위치 정보가 없을 때는 업데이트 안 함
        //상황에 따라 짧아질 수 있음, 정확하지 않음
        //다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest.interval = 10000
        //정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000



    }//locationInit




    /*
       - 사용가능한 맵을 조작한다.
       - 지도를 사용할 준비가 되면 이 콜백이 호출된다.
       - 여기서 마커나 선, 청취자를 추가하거나 카메라를 이동할 수 있다.
       - 호주 시드니 근처에 마커를 추가하고 있다.
       - Google Play 서비스가 기기에 설치되어 있지 않은 경우 사용자에게
       SupportMapFragment 안에 Google Play 서비스를 설치하라는 메시지가 표시된다.
       - 이 메서드는 사용자가 Google Play 서비스를 설치하고 앱으로 돌아온 후에만
       호출(혹은 실행) 된다.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap //지도가 준비되면 GoogleMap 객체를 얻는다.

        // 시드니를 추가하고 카메라를 이동한다
        // > 위도와 경도로 시드니의 위치를 정하고 구글 지도 객체에 마커를 추가하고 카메라를 이동한다
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }//onMapReady

    override fun onResume() {
        super.onResume()

        /*
            11)addLocationListener() 메서드를 호출하기 전에 9)permissionCheck() 메서드를 이용하여 권한을 요청한다
            첫 번째 인자인 cancel() 함수는 이전에 사용자가 권한 요청을 거부했을 때 호출
            10)권한이 필요한 이유를 알려주는 다이얼로그를 표시한다.
            두 번째 인자인 ok() 함수는 사용자가 권한을 수락했을 때 호출
            11)주기적인 위치 정보 갱신을 시작한다.
         */
        //권한요청 -9
        permissionCheck(cancel = {
            //위치 정보가 필요한 이유 다이얼로그 표시 -10
            showPermissionInfoDialog()
        }, ok = {
            //현재 위치를 주기적으로 요청(권한이 필요한 부분) -11
            //이러한 위치 요청은 액티비티가 활성화되는 onResume() 메서드에서 수행하며
            //별도의 메서드로 작성한다.
            addLocationListener()
        })




    }//onResume

    @SuppressLint("MissingPermission")
    private  fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null)
    }//addLocationListener


    //requestLocationUpdates() 메서드에 전달되는 인자 중 LocationCallBack을 구현한
    //내부 클래스는 LocationResult 객체를 반환하고 lastLocation 프로퍼티로 Location 객체를 얻는다.
    inner class MyLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            //기기의 GPS 설정이 꺼져 있거나 현재 위치 정보를 얻을 수 없는 경우에 Location 객체가 null일 수 있다.
            //Location 객체가 null이 아닐 때 해당 위도와 경도 위치로 카메라를 이동한다.
            location?.run {
                //14 level로 확대하고 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                
                Log.d("MapsActivity","위도:$latitude, 경도:$longitude")
                //>Location 객체의 위도와 경도값을 로그로 출력

                //PolyLine에 좌표 추가
                polylineOptions.add(latLng)

                //선그리기
                mMap.addPolyline(polylineOptions)
            }

        }//onLocationResult


    }//class MyLocationCallback

    /*
        실행 중 권한 요청 메서드 작성
        - 코틀린의 고차 함수 기능을 사용하여 권한을 요청할 때마다 활용하는 메서드를 작성한다.
        -1) 이 메서드는 함수 인자 두 개를 받는다. 두 함수는 모두 인자가 없고 반환값도 없다.
        이전에 사용자가 권한 요청을 거부한 적이 있다면 2)cancel()함수를 호출하고,
        권한이 수락되었다면 3)ok() 함수를 호출한다.
     */
    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit){    //-1
        //위치 권한이 있는지 검사
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.
                    permission.ACCESS_FINE_LOCATION)){
                //이전에 권한을 한 번 거부한 적이 있는 경우에 실행할 함수
                cancel() //-2
            } else {
                //권한 요청
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            //권한을 수락했을 때 실행할 함수
            ok() //-3
        }//if





    }//permissionCheck

    /*
        권한이 필요한 이유를 설명하는 다이얼로그 표시 메서드 추가
        - 이 메서드는 4)위치정보가 필요한 이유를 설명하는 AlertDialog를 8)표시한다.
        이 다이얼로그는 5)긍정버튼과 7)부정버튼을 가지고 있고,
        5)긍정버튼을 누르면 권한을 요청하고
        7)부정 버튼을 누르면 아무것도 하지 못하고 다이얼로그가 닫힌다.
        requestPermissions() 메서드의 6)첫 번째 인자는 Context 또는 Activity를 전달한다.
        yesButton{} 블럭 내부에서 this는 DialogInterface 객체를 나타낸다.
        따라서 액티비티를 명시적으로 가리키려면 this@MapsActivity로 작성해야 한다.
     */
    private fun showPermissionInfoDialog(){
        alert ("현재 위치 정보를 얻으려면 위치 권한이 필요합니다", "권한이 필요한 이유") { //-4
            yesButton {//-5
                //권한요청
                ActivityCompat.requestPermissions(this@MapsActivity, //-6
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton {  } //-7
        }.show() //-8
    }//howPermissionInfoDialog

    /*
        권한 요청 처리
        - 사용자가 권한을 수락하면 1)addLocationListener() 메서드를 호출하여 위치 정보를 갱신한다.
        거부하면 2)토스트 메시지를 표시
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if((grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    //권한 허용됨-1
                    addLocationListener()
                } else {
                    //권한 거부-2
                    toast("권한 거부 됨")
                }//if
                return
            }
        }//when
    }//onRequestPermissionsResult

    /*
        onResume() 메서드에서 위치 정보를 요청해서 앱이 동작 중일 때만 위치 정보를 갱신한다.
        액티비티의 생명주기에 따라서 앱이 동작하지 않을 때 위치 정보 요청을 삭제해야 한다면 onPause() 메서드에서 한다
        - onPause() 메서드에서 1)위치 요청을 취소한다. 위치 요청을 취소하는 removeLocationListener() 메서드에서는
        2)removeLocationUpdates() 메서드에 LocationCallback 객체를 전달하여 주기적인 위치 정보 갱신 요청을 삭제한다.
     */
    override fun onPause() {
        super.onPause()
        //-1
        removeLocationListener()
    }//onPause

    private fun removeLocationListener() {
        //현재 위치 요청을 삭제 -2
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }//removeLocationListener



}//class
