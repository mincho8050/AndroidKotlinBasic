package com.example.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

/*
    서비스가 플래시를 On/Off하는 코드 작성
    - 1)TorchService 서비스는 Service 클래스를 상속받는다
    - 2)TorchService 서비스가 Torch 클래스를 사용해야 한다. Torch 클래스의 인스턴스를 얻는 방법에는
    onCreate() 콜백 메서드를 사용하는 방법과 by lazy를 사용하는 방법이 있다.
    onCreate() 콜백 메서드를 사용하면 코드가 더 길어지기 때문에 여기서는 by lazy를 사용한 초기화 지연
    방법을 사용했다. 이 방법을 사용하면 torch 객체를 처음 사용할 때 초기화 된다.
    - 3)외부에서 startService() 메서드로 TorchService 서비스를 호출하면 onStartCommand() 콜백 메서드가 호출된다.
    보통 인텐트에 action값을 설정하여 호출하는데 "on"과 "off" 문자열을 액션으로 받았을 때 when문을 사용하여
    각각 플래시를 켜고 끄는 동작을 하도록 코드를 작성한다.
    - 4)onStartCommand() 메서드는 다음 중 하나를 반환한다. 이 값에 따라 시스템이 강제로 종료한 후에
    시스템 자원이 회복되어 다시 서비스를 시작할 수 있을 때 어떻게 할지를 결정한다.

    -START_STICKY : null 인텐트로 다시 시작한다. 명령을 실행하지는 않지만 무기한으로 실행 중이며
    작업을 기다리고 있는 미디어 플레이어와 비슷한 경우에 적합
    -START_NOT_STICKY : 다시 시작하지 않음
    -START_REDELIVER_INTENT : 마지막 인텐트로 다시 시작함. 능동적으로 수행 중인 파일 다운로드와 같은 서비스에 적합

    일반적인 경우에는 4)super클래스의 onStartCommand() 메서드를 호출하면 내부적으로
    START_STICKY를 반환한다.

 */
class TorchService : Service() {    //-1

    private val torch: Torch by lazy { //-2
        Torch(this)
    }
    
    private var isRunning = false
    //>플래시가 꺼졌는지  켜졌는지 알기 위해 변수 추가.

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action){      //-3
            //앱에서 실행할 경우
            "on" -> {
                torch.flashOn()
                isRunning = true //켜졌을때
            }
            "off" -> {
                torch.flashOff()
                isRunning = false //꺼졌을때
            }
            //서비스에서 실행할 경우
            //>여기서 isRunning값에 따라서 플래시를 켜거나 끄는 동작이 결정된다
            else -> {
                isRunning = !isRunning
                if (isRunning){
                    torch.flashOn()
                }else{
                    torch.flashOff()
                }
            }
        }//when
        return super.onStartCommand(intent, flags, startId) //-4
    }//onStartCommand


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }//onBind
}//class
