package com.example.bmicalculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //전달받은 키와 몸무게
        val height = intent.getStringExtra("height").toInt()
        val weight = intent.getStringExtra("weight").toInt()

        //BMI 계산
        //> 키를 100으로 나눈 제곱을 몸무게에서 나누면 BMI값이 나옴
        val bmi=weight/Math.pow(height/100.0,2.0)

        //결과 표시
        //> when을 사용하여 BMI값이 특정 구간에 있으면 해당하는 메시지를 표시한다
        when{
            bmi >= 35 -> resultTextView.text = "고도 비만"
            bmi >= 30 -> resultTextView.text = "2단계 비만"
            bmi >= 25 -> resultTextView.text = "1단계 비만"
            bmi >= 23 -> resultTextView.text = "과체중"
            bmi >= 18.5 -> resultTextView.text = "정상"
            else -> resultTextView.text = "저체중"
        }//when

        //> 여기까지는 BMI가 어떤 값이 나와도 같은 이미지가 표시된다. 이를 BMI값에 따라서
        //> 다른 이미지가 나오도록 when을 사용하여 구간마다 이미지를 교체한다.

        //이미지 표시
        //> when을 사용하여 구간마다 이미지를 교체한다.
        when {
            bmi >= 23 ->
                imageView.setImageResource(
                    R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 ->
                imageView.setImageResource(
                    R.drawable.ic_sentiment_satisfied_black_24dp
                )
            else ->
                imageView.setImageResource(
                    R.drawable.ic_sentiment_dissatisfied_black_24dp
                )


        }//when

        /*
            Toast를 사용하여 간단한 메시지를 표시하기
            - 안드로이드 기기를 사용하다 보면 하단에 잠깐 보였다 사라지는 메시지를 본 적이 있을 겁니다.
            - 이것을 토스트(Toast) 메시지라고 한다.
            - 토스트를 이용하여 BMI값을 표시할 수 있다.
         */

        //BMI값을 표시하는 토스트 코드
        //BMI값이 Double형이므로 $ 기호를 사용하여 문자열 템플릿에 적용하기
        Toast.makeText(this, "$bmi",Toast.LENGTH_SHORT).show()

        //토스트 코드에 Anko를 적용하면 다음과 같이 줄일 수 있다.
        toast("$bmi")

    }//
}//
