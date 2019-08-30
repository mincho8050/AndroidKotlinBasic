package com.example.mygallery

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000


    /*
        권한 확인
        실행 중에 위험 권한이 필요한 작업을 수행할 때마다 권한이 있는지 확인해야 한다.
        권한은 사용자가 앱 설정에서 언제든지 취소할 수 있기 때문이다. 예를 들어 어제 저장소 읽기 권한이
        있었더라도 오늘도 권한이 있다고는 할 수 없다.
        권한이 있는지 확인하려면 ContextCompat.checkSelfPermission() 메서드를 사용한다.
        다음 코드는 앱이 외부 저장소 읽기 권한이 있는지 확인한다.
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
            권한 요청
            -1) 앱에 권한이 부여되었는지 확인
            -2) shouldShowRequestPermissionRationale() 메서드는 사용자가 전에 권한 요청을 거부 했는지 반환.
            true를 반환하면 거부를 한 적이 있는 것
            -3) 권한이 왜 필요한지에 대해 별도의 메시지를 표시하고 다시 권한을 요청할 수 있다.
            -4) requestPermissions() 메서드를 사용하여 외부 저장소 읽기 권한을 요청.
            -5) 적당한 정수값을 넣는다. 이 값은 권한 요청에 대한 결과를 분기 처리하는데 사용
         */
        //권한이 부여되었는지 확인-1
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            
            //권한이 허용되지 않음-2
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                //이전에 이미 권한이 거부되었을 때 설명-3  
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다","권한이 필요한 이유"){
                    yesButton{
                        //권한 요청-4
                        ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton{}
                }.show()
            } else{
                //권한요청-5
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE)
            } //2st if
        } else {
            //권한이 허용됨
            getAllPhotos()
        }//1st if
    }//onCreate

    /*
        [사용 권한 요청 응답 처리]
        -권한 요청에 대한 결과 처리
        - 사용자가 권한을 요청하면 시스템은 onRequestPermissionsResult() 메서드를 호출하고 사용자의 응답을 전달한다.
        권한이 부여되었는지 확인하려면 이 메서드를 오버리아드해야 한다. 응답 결과를 확인하여 사진 정보를 가져오거나
        권한이 거부됐다는 토스트 메시지를 표시하는 코드를 작성한다. onRequestPermissionsResult() 메서드를 
        오버라이드할 때는 자동 완성 기능을 사용하면 편리하다
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //권한 허용됨
                    getAllPhotos()
                } else {
                    //권한 거부
                    toast("권한 거부됨")
                }//if
                return
            }//
        }//when


    }//onRequestPermissionsResult


    /*
        [모든 사진을 가져오는 코드 작성]
        기기의 사진 경로얻기
        - 프로바이더를 사용해 사진 정보를 얻으려면 contentResolver 객체를 사용해 데이터를 얻을 수 있다.
        다음은 외부 저장소에 저장된 모든 사진을 최신순으로 정렬하여 Cursor라는 개체를 얻는 코드.
        -1) 첫 번째 인자는 어떤 데이터를 가져오느냐를 URI 형태로 지정. 사진 정보는 외부 저장소에 저장되어 있기 때문에
        외부 저장소에 저장된 데이터를 가리키는 URI인 EXTERNAL_CONTENT_URI를 지정한다.
        -2) 두 번째 인자는 어떤 항목의 데이터를 가져올 것인지 String 배열로 지정. 가져올 데이터의 구조를
        잘 모른다면 일반적으로 null을 지정한다. null을 지정하면 모든 항목을 가져온다.
        -3) 세 번째 인자는 데이터를 가져올 조건을 지정할 수 있다. 전체 데이터를 가져올때는 null을 지정
        -4) 네 번째 인자는 세 번째 인자와 조합하여 조건을 지정할 때 사용. 사용하지 않는다면 null
        -5) 정렬 방법 지정. 사진이 찍힌 날짜의 내림차순 정렬
        -> 더 복잡한 데이터 요청도 할 수 있다. SQL 문법 지식이 필요하다
     */
    /*
        사진 정보 로그로 표시
        - 사진 정보가 잘 읽어지는지 로그로 확인하는 코드를 getAllPhotos() 메서드에 추가
     */
    private fun getAllPhotos(){
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //1
            null,           //2.가져올 항목
            null,           //3.조건
            null,       //4.조건
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") //5.찍은날짜수
        //> " DESC" 앞에 띄어쓰기 주의하자


        /*
            -1) 사진 정보를 담고 있는 Cursor 객체는 내부적으로 데이터를 이동하는 포인터를 가지고 있어서
            moveToNext() 메서드로 다음 정보로 이동하고 그 결과를 true로 반환.
            while문을 사용하면 모든 데이터를 순회할 수 있다. 만약 사진이 없다면 Cursor 객체는 null입니다.
            -2) 사진의 경로가 저장된 데이터베이스의 칼럼명은 DATA 상수에 정의되어 있다.
            getColumnIndexOrThrow() 메서드를 사용하면 해당 칼럼이 몇 번째 인덱스인지 알 수 있다.
            getString() 메서드에 그 인덱스를 전달하면 해당하는 데이터를 String 타입으로 반환.
            이것이 uri 즉 사진이 저장된 위치의 경로.
            -3) Cursor 객체를 더 이상 사용하지 않을 때는 close() 메서드로 닫아야 한다.
            만약 닫지 않으면 메모리 누수!!!
         */
        /*
            전자액자 완성
            -4) 프래그먼트를 아이템으로 하는 ArrayList를 생성. Cursor 객체로부터 가져올때마다
            -5) PhotoFragment.newInstance(uri)로 프래그먼트를 생성하면서 fragments리스트에 추가
            -6) MyPagerAdapter를 생성하면서 프래그먼트 매니저를 생성자의 인자로 전달해야함.
         */
        //-1
        val fragments = ArrayList<Fragment>() //-4
        if (cursor != null){
            while (cursor.moveToNext()){
                //사진 경로 Uri 가져오기 -2
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity",uri)
                fragments.add(PhotoFragment.newInstance(uri)) //-5
            }//while
            cursor.close() //-3
        }//if

        //-6
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragment(fragments)
        viewPager.adapter = adapter

        /*
            [timer를 사용하여 자동 슬라이드쇼 구현]
            3초마다 자동 슬라이드
            -1) 3초마다 실행되는 타이머 생성.
            -2) 3초마다 실행될 내용은 페이지를 전환하는 UI변경이다.
            timer가 백그라운드 스레드로 동작해 UI를 변경하도록 runOnUiThread로 코드를 감싼다
            -3) 현재 페이지가 마지막 페이지가 아니라면
            -4) 다음페이지로 변경하고, 
            마지막 페이지라면
            -5) 첫 페이지로 변경한다
         */
        timer(period = 3000) {  //-1
            runOnUiThread {     //-2
                if(viewPager.currentItem < adapter.count -1){           //-3
                    viewPager.currentItem = viewPager.currentItem +1    //-4
                } else {
                    viewPager.currentItem = 0                           //-5
                }
            } //runOnUiThread
        }//timer



    }//getAllPhotos



}//MainActivity




