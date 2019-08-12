package com.example.xylophone

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //이 코드는 API21 이상부터 지원함. 
    //private val soundPool = SoundPool.Builder().setMaxStreams(8).build()

    //if문으로 버전에 따른 분기를 하는 코드
    //생성된 코드에 다음과 같이 구 버전용 SoundPool 객체 초기화 코드를 추가한다.
    //자동으로 추가된 if문이 사용되어 롤리팝 이후와 이전에 다른 코드가 수행되게 된다.
    private val soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        SoundPool.Builder().setMaxStreams(8).build()
    } else {
        SoundPool(8,AudioManager.STREAM_MUSIC, 0)
        //여전히.. 더 이상 사용되지 않은 생성자로 표시되는뎁..
    }
    //private val soundPool = SoundPool(8, AudioManager.STREAM_MUSIC, 0)
    //>이 생성자는 더 이상 사용되지 않음

    /*
        [음판에 동적으로 클릭 이벤트 정의하기]
        음판을 눌렀을 때 해당 음계를 재생하도록 다음과 같이 코드를 작성한다. 미리 텍스트 뷰와 음원을 연관 지은
        리스트를 준비하여 이 리스트를 반복 순회하여 클릭 이벤트를 정의한다.
        - 1)먼저 listOf()함수를 사용하여 텍스트 뷰의 ID와 음원 파일의 리소스 ID를 연관 지은 Pair 객체 8개를
        리스트 객체 sounds로 만든다. Pair 클래스는 두 개의 연관된 객체를 저장한다.
        - 2)sounds 리스트를 forEach() 함수를 사용하여 요소를 하나씩 꺼내서 tune() 메서드에 전달한다.
        - 3)tune() 메서드는 Pair 객체를 받아서 4)load() 메서드로 음원의 ID를 얻고
        5)findViewById() 메서드로 텍스트 뷰의 ID에 해당하는 뷰를 얻고 6)텍스트 뷰를 클릭했을 때 음원을 재생한다.
        - 7)음원을 종료할 때는 반드시 release() 메서드를 호출하여 SoundPool 객체의 자원을 해제한다.
     */

    private val sounds = listOf(    //-1
        Pair(R.id.do1, R.raw.do1),
        Pair(R.id.re, R.raw.re),
        Pair(R.id.mi, R.raw.mi),
        Pair(R.id.fa, R.raw.fa),
        Pair(R.id.sol, R.raw.sol),
        Pair(R.id.la, R.raw.la),
        Pair(R.id.si, R.raw.si),
        Pair(R.id.do2, R.raw.do2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sounds.forEach { tune(it) } //-2

    }//onCreate

    private fun tune(pitch: Pair<Int, Int>){    //-3
        val soundId = soundPool.load(this, pitch.second, 1) //-4
        findViewById<TextView>(pitch.first).setOnClickListener{     //-5
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f) //-6
        }
    }//tune

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }//onDestroy



}//
