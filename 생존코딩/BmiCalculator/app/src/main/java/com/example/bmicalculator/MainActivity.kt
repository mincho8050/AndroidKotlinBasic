package com.example.bmicalculator

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //이전에 입력한 값을 읽어오기
        loadData()

        resultButton.setOnClickListener {

            //1) 결과 버튼이 클릭되면 할 일을 작성하는 부분
            //val intent= Intent(this, ResultActivity::class.java)
            //2) 인텐트는 데이터를 담아서 다른 액티비티에 전달하는 역할도 함.
            // 다음은 인텐트에 데이터를 담는 코드이다.
            // 입력한 키와 몸무게를 문자열로 변경하여 인텐트에 담고 있다.
            // putExtra() 메서드에 키(key)와 값(value)의 쌍으로 데이터 저장
            //intent.putExtra("weight",weightEditText.text.toString())
            //intent.putExtra("height",heightEditText.text.toString())
            //startActivity(intent)
            //>액티비티로 전환하는 코드. 기니까 Anko라이브러리를 이용해 코드를 짧게 하자.

            //마지막에 입력한 내용 저장
            saveData(heightEditText.text.toString().toInt(),
                weightEditText.text.toString().toInt())


            //>1) Anko 라이브러리를 적용한 코틀린의 액티비티 전환 코드
            // 2) Anko를 적용한 코드. 클릭 이벤트를 처리하는 부분에 다음과 같이 코드 추가
            //>버튼을 누르면 두번째 화면으로 이동하는 코드
            startActivity<ResultActivity>(
                "weight" to weightEditText.text.toString(), //입력한 키와 몸무게를 문자열로 변경하여 인텐트에 담고 있다.
                "height" to heightEditText.text.toString()
            )

        }




    }//onCreate

    //데이터 저장하기
    //> 마지막으로 입력했던 키와 몸무게가 다음 번 앱을 실행했을 때 남아있도록
    //> SharedPreference에 저장하고 복원하는 기능
    //> 키와 몸무게를 저장하는 메서드를 작성
    private fun saveData(height:Int , weight:Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this) //1)프리퍼런스 매니저를 사용해 프리퍼런스 객체를 얻는다
        val editor = pref.edit()//2) 프리퍼런스 객체의 에디터 객체를 얻는다. 이 객체를 사용해 프리퍼런스에 데이터를 담을 수 있다.

        editor.putInt("KEY_HEIGHT", height)//3) 에디터 객체에 put[데이터타입] 형식의 메서드를 사용하여 키와 값 형태의 쌍으로
            //저장을 한다. put 메서드는 기본 타입은 모두 지원한다.
            .putInt("KEY_WEIGHT",weight)
            .apply()//4) 마지막으로 설정한 내용을 반영한다

    }//savaData

    /*
        결과 버튼을 클릭할 때 화면을 전환하기 전에 saveDate() 메서드를 호출하여 프리퍼런스에
        키와 몸무게 값을 저장해본다. onCreate() 메서드에 다음과 같이 코드를 추가한다
     */


    /*
        저장한 데이터를 불러오는 메서드를 추가한다.
     */

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)//1) 프리퍼런스 객체를 얻는다.
        val height = pref.getInt("KEY_HEIGHT",0)//2) getInt() 메서드로 키(KEY_HEIGHT)와 몸무게(KEY_WEIGHT)에 저장된 값 불러오기
        //getInt() 메서드의 두 번째 인자는 저장된 값이 없을 때 기본값 0을 리턴한다는 의미
        val weight = pref.getInt("KEY_WEIGHT",0)

        if (height != 0 && weight != 0){//3) 키와 몸무게가 모두 0인 경우. 즉, 저장된 값이 없을 때는 아무것도 안함
            heightEditText.setText(height.toString())//4) 저장된 값이 있다면 키와 몸무게를 입력하는 에디트텍스트에 마지막에
                                                        //저장된 값을 표시한다
            weightEditText.setText(weight.toString())
        }//if

        //loadDate() 메서드를 onCreate() 메서드에 추가한다

    }//loadData

}//
