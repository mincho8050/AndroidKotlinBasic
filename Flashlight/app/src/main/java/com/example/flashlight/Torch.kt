package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

/*
    손전등 클래스 코드
    - 이 클래스는 플래시를 켜는 5)flashOn() 메서드와 플래시를 끄는 6)flashOff() 메서드 기능을 제공한다.
    플래시를 켜려면 3)CameraManager 객체가 필요하고 이를 얻으려면 Context 객체가 필요하기 때문에
    1)생성자로 Context를 받는다. 2)카메라를 켜고 끌때(5,6) 카메라 ID가 필요하다. 클래스 초기화 때
    4)카메라 ID를 얻어야 한다. 카메라 ID는 기기에 내장된 카메라마다 고유한 ID가 부여된다.
    3)context의 getSystemService() 메서드는 안드로이드 시스템에서 제공하는 각종 서비스를 관리하는
    매니저 클래스를 생성한다. 인자로 Context 클래스에 정의된 서비스를 정의한 상수(여기서는 CAMERA_SERVICE)를 지정한다.
    이 메서드는 Object형을 반환하기 때문에 as 연산자를 이용해 CameraService형으로 형변환한다.
    7)getCameraId() 메서드는 카메라의 ID를 얻는 메서드다. 카메라가 없다면 ID가 null일 수 있기 때문에
    반환값은 String?로 지정한다.
    8)CameraManager는 기기가 가지고 있는 모든 카메라에 대한 정보 목록을 제공한다.
    9)이 목록을 순회하면서 10)각 ID별로 세부 정보를 가지는 객체를 얻는다
    이 객체로부터 11)플래시 기능 여부와 12)카메라의 렌즈 뱡향을 알 수 있다.
    13)플래시가 사용 가능하고 카메라가 기기의 뒷면을 향하고 있는 카메라의 ID를 찾았다면
    14)이 값을 반환한다.
    해당하는 카메라 ID를 찾지 못했다면 15)null을 반환한다.
    -에뮬레이터는 카메라가 없어 null을 반환한다.ㄴ
 */
class Torch (context: Context) {    //-1
    private var cameraId: String? = null//-2
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager//-3

    init {  //-4
        cameraId = getCameraId()
    }//init

    fun flashOn(){  //-5
        cameraManager.setTorchMode(cameraId,true)
    }//flashOn

    fun flashOff(){ //-6
        cameraManager.setTorchMode(cameraId, false)
    }//flashOff

    private fun getCameraId():String? {    //-7
        val cameraIds = cameraManager.cameraIdList  //-8
        for (id in cameraIds) { //-9
            val info = cameraManager.getCameraCharacteristics(id)                       //-10
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)   //-11
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)                //-12
            if (flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK){   //-13
                return id   //-14
            }//if
        }//for
        return null //-15
    }//getCameraId




}//class