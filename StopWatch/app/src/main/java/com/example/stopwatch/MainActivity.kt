package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    //지역변수
    private var time = 0 //시간을 계산할 변수를 0으로 초기화 선언
    private var isRunning =false
    private var timerTask:Timer?=null //timerTask 변수를 null을 허용하는 Timer 타입으로 선언
    //var 변수명 : 자료명 = 값
    private var lap=1;
    //>몇번째 랩인지를 표시하고자 변수 lap을 1로 초기화.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //3)버튼에 이벤트 연결
        fab.setOnClickListener {
            isRunning = !isRunning
            //>fab이 클릭되면 타이머가 동작 중인지를 저장하는 isRunning 변수의 값을 반전시킨다.
            //>isRunning은 처음에 false로 되어있다.

            if(isRunning){//만약 true라면
                start()
            } else {//false라면
                pause()
            }
        }//

        //랩 타임 버튼에 이벤트 연결!!
        lapButton.setOnClickListener {
            recordLapTime()
        }

        //초기화 버튼에 이벤트 연결
        resetFab.setOnClickListener {
            reset()
        }

    }//onCreate()


    //1)타이머 시작 구현
    //>타이머를 시작할 때 호출할 start() 메서드 작성
    private fun start(){

        fab.setImageResource(R.drawable.ic_pause_black_24dp) //타이머를 시작하는 FAB를 누르면 FAB이미지를 일시정지 이미지로 변경

        timerTask = timer(period = 10){//1)0.01초 마다 이 변수를
            time++//2)증가시키면서

            val sec = time/100
            val milli=time%100

            runOnUiThread { //3)UI를 갱신한다
               secTextView.text = "$sec"//$ 문자열 리터럴 내부에 변수를 넣을 수 있음
                milliTextView.text = "$milli"


            }//
        }//timer

        /*
            나중에 timer를 취소하려면 timer를 실행하고 반환되는 Timer 객체를 변수에 저장해둘 필요가 있다.
            이를 위해 timerTask 변수를 null을 허용하는 Timer 타입으로 선언했다.

            시간이 변경되면 화면에 변경되 시간을 표시해야 한다. timer는 워커 스레드에서 동작하여 UI조작이 불가능해서
            runOnUiThread로 감싸서 UI 조작이 가능하게 한다. 그리고 계산한 초와 밀리초를 각각의 텍스트 뷰에 설정한다.
         */

    }//start()

    //2)타이머 일시정지 구현
    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        //>타이머 시작과 반대로 fab클릭하면 플레이버튼이 보이게 한다.(시작이미지로 교체)
        timerTask?.cancel()//메서드 호출 시 점. 연산자 대신 ?. 연산자를 사용하면 null값이 아닌 경우에만 호출
        //>그리고 실행중인 타이머가 있다면 타이머를 취소한다
    }//pause

    //3)랩 타임 기록 메서드
    private fun recordLapTime(){

        val lapTime = this.time //랩 타임 버튼을 클릭하면 현재 시간을 지역 변수에 저장한다->1
        val textView = TextView(this)//동적으로 TextView를 생성하여 텍스트값을 ->2
        textView.text = "$lap LAP : ${lapTime/100}.${lapTime%100}"
        //>'1 LAP : 5.35'와 같은 형태가 되도록 시간을 계산하여 문자열로 설정 ->3

        //맨 위에 랩타임 추가 -> 4
        //텍스트 뷰를 LinearLayout의 맨 위에 추가하고 lap변수는 다음을 위해 1만큼 증가시킨다.
        lapLayout.addView(textView,0)
        lap++



    }//recordLapTime

    //4)타이머 초기화 구현
    private fun reset(){

        timerTask?.cancel()
        //4-1)실행중인 타이머가 있다면 취소하고
        //>그러니까 동작중이면(플레이중이면) 일단 멈추고!!

        //4-2)모든변수 초기화
        //>화면에 표시되는 모든 것을 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        //4-3)모든 랩타임 제거
        lapLayout.removeAllViews()
        lap=1

    }//reset



}//
