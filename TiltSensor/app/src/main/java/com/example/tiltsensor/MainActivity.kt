package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    //센서 정밀도가 변경되면 호출된다
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }//onAccuracyChanged

    /*
        센서값이 변경되면 호출된다.
        - 센서값이 변경되면 onSensorChanged() 메서드가 호출되며 SensorEvent 객체에
        센서가 측정한 값들과 여러 정보가 넘어온다.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        //센서값이 변경되면 호출됨
        //values[0] : x축 값 : 위로 기울이면 -10~0, 아래로 기울이면 0~10
        //values[1] : y축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        //values[2] : z축 값 : 미사용
        //디버그용 로그를 표시할 때 사용
        //Log.d([태그], [메시지]) :
        //이 외에도 여러 로그 메시지가 있다.
        //Log.e() : 에러를 표시 / Log.w() : 경고표시
        //Log.i() : 정보성 로그 표시 / Log.v() : 모든 로그 표시
        event?.let {
            Log.d("MainActivity","onSensorChanged: x:" +
                    "${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")

            tiltView.onSensorEvent(event)
            //>호출. 액티비티에서 센서값이 변경되면 onSensorChanged() 메서드가 호출되므로
            //> 여기서 TiltView에 센서값을 전달한다.

                }
    }//onSensorChanged


    //sensorManager 준비
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }


    private lateinit var tiltView: TiltView //TiltView의 늦은 초기화 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
            화면이 꺼지지 않게 하기.
            - 절전 기능이 활성화되어있으면 사용자의 조작이 없을때 자동으로 화면이 꺼진다.
            - 센서를 사용 중인데 화면이 꺼지면 불편하다.
            - 이 액티비티를 실행하는 중에는 화면이 꺼지지 않도록 플래그 설정
         */
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //window.addFlags() 메서드에는 액티비티 상태를 지정하는 여러 플래그 설정가능
        
        /*
            화면이 가로로 고정되게 하기
            - 기기의 방향을 가로로 고정하려면 슈퍼클래스의 생성자를 호출하기 전에
            -requestedOrientation 프로퍼티값에 가로 방향을 나타내는
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE값을 설정한다
         */
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiltView = TiltView(this)   //onCreate() 메서드에서 생성자에 this를 넘겨 TiltView를 초기화
        setContentView(tiltView)//기존의 R.layout.activity_main 대신에 tiltView를 setContentView() 메서드에 전달한다.
                                //이제 tiltView가 전체 레이아웃이 된다


    }//onCreate()

    /*
        센서등록
        - SensorManager 객체가 준비되면 액티비티가 동작할 때만 센서가 동작해야
        - 배터리를 아낄 수 있다. 일반적으로 센서의 사용등록은 액티비티의 onResume() 메서드에 수행
        1) registerListener() 메서드로 사용할 센서를 등록.
        첫번째 인자는 센서값을 받을 SensorEvenListener다. 여기서는 this를 지정하여 액티비티에서 센서값을 받도록한다.
        2)그런다음 getDefaultSensor() 메서드로 사용할 센서 종류를 지정한다. 
        여기서는 Sensor 클래스에 상수로 정의된 가속도센서를 지정한다.
        3)세 번째 인자는 센서값을 얼마나 자주 받을지를 지정한다.
        SensorManager 클래스에 정의 된 상수 중 하나를 선택한다.

        SENSOR_DELAY_FASTEST : 가능한 가장 자주 센서값을 얻는다.
        SENSOR_DELAY_GAME : 게임에 적합한 정도로 센서값을 얻는다.
        SENSOR_DELAY_NORMAL : 화면 방향이 전환될 때 적합한 정도로 센서값을 얻는다.
        SENSOR_DELAY_UI : 사용자 인터페이스를 표시하기에 적합한 정도로 센서값을 얻는다.

     */
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,    //1
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//2
            SensorManager.SENSOR_DELAY_NORMAL)//3
    }//onResume


    /*
        센서 해제
        - 액티비티가 동작 중일 때만 센서를 사용하려면 화면이 꺼지기 직전인
        onPause() 메서드에서 센서를 해제한다.
        unregisterListener() 메서드를 이용하여 센서 사용을 해제할 수 있으며
        인자로 SensorEvenListener 객체를 지정한다. MainActivity 클래스에서
        이 객체를 구현 중이므로 this를 지정한다.
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }//onPause
    
    
    
}//
