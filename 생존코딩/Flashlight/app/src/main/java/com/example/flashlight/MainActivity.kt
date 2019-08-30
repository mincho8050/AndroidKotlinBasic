package com.example.flashlight

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        /*
            [손전등 켜고 끄는 코드 작성]
            - Switch 는 켜거나 끄는 두 가지 상태값을 가지는 버튼 객체이다.
            setOnCheckedChangeListener를 구현하면 상태가 변경되었을 때의 처리를 수행
            
            1)작성한 Torch 클래스를 인스턴스화 한다.
            2)스위치가 켜지면 flashOn() 메서드를 호출하여 플래시를 켜고
            3)스위치가 꺼지면 flashOff() 메서드를 호출하여 플래시를 끈다.
            앱을 안드로이드 6.0 이상 실제 기기에서 실행하면 플래시가 잘 작동하면 성공
         */
        /*
            [액티비티에서 서비스를 사용해 손전등 켜기]
            - 서비스를 시작하려면 startService() 메서드를 사용한다.
            - 다음은 TorchService를 사용해서 플래시를 켜는 인텐트에 "on"액션을 설정하여 서비스를 시작하는 코드
         */
        val torch = Torch(this) //-1

        flashSwitch.setOnCheckedChangeListener { buttonView, isChecked -> 
            if (isChecked){
                //torch.flashOn() //-2
                startService(intentFor<TorchService>().setAction("on")) //서비스를 사용해 손전등 켜기
            } else {
                //torch.flashOff()  //-3
                startService(intentFor<TorchService>().setAction("off"))
            }
        }//
        


    }//onCreate




}//class
