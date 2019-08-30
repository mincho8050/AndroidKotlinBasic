package com.example.diceroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val rollButton:Button=findViewById(R.id.roll_button)
        rollButton.text="Let's Roll"
        findViewById()를 사용해도 되지만 바로 버튼 id에 text를 넣을 수 있는것이
        코틀린이다!!
        */
        roll_button.text="Let's Roll"

        //버튼에 대한 OnClickListener 설정:
        //이 코드는 롤버튼이 토스트 메시지를 보여주게 할 것이다.
        /*roll_button.setOnClickListener {
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        }*/

        roll_button.setOnClickListener {
            rollDice()
            //>버튼을 클릭하면 rollDice()함수를 호출
        }

    }//onCreate

    /*
        roll_button을 누르면 텍스트 내용이 변함
     */
    private fun rollDice() {
        /*
        텍스트에 접근하고 싶을때..
        val resultText:TextView=findViewById(R.id.result_text)
        resultText.text="Dice Rolled"*/
        //여기서 Random()은 java.util 사용..
        //랜덤으로 숫자를 발생시키는 함수
        val randomInt=Random().nextInt(6)+1
        
        //ImageView에 있는 android:src="@drawable/dice_1" 를 바꿔주는듯
        val drawableResource = when(randomInt){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }//when

        dice_image.setImageResource(drawableResource)


    }//rollDice()



}//
