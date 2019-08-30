package com.example.colormyviews

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

    }//onCreate


    private fun setListeners(){
        val clickableViews:List<View> =
            listOf(box_one_text, box_two_text, box_three_text,
                    box_four_text, box_five_text,constraint_layout,
                    red_button, green_button, yellow_button)
        for(item in clickableViews){
            item.setOnClickListener { makeColored(it) }
        }
    }//setListeners



    private fun makeColored(view:View){
        when(view.id){
            //배경에 색 클래스 색상을 사용한 상자
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

            //Android 색상 리소스를 사용하는 상자
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)

            //배경에 색 클래스 색상을 사용한 상자
            R.id.red_button -> box_three_text.setBackgroundResource(R.color.my_red)
            R.id.yellow_button -> box_four_text.setBackgroundResource(R.color.my_yellow)
            R.id.green_button -> box_five_text.setBackgroundResource(R.color.my_green)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }//makeColored



}//
