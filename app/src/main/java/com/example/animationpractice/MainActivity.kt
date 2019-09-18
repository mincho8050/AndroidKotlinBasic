package com.example.animationpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fade_in.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.fade_in)
            textView.startAnimation(animation)
        }
        fade_out.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.fade_out)
            textView.startAnimation(animation)
        }
        zoom_in.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.zoom_in)
            textView.startAnimation(animation)
        }
        zoom_out.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.zoom_out)
            textView.startAnimation(animation)
        }
        slide_down.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.slide_down)
            textView.startAnimation(animation)
        }
        slide_up.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.slide_up)
            textView.startAnimation(animation)
        }
        bounce.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.bounce)
            textView.startAnimation(animation)
        }
        rotate.setOnClickListener {
            val animation=AnimationUtils.loadAnimation(this,R.anim.rotate)
            textView.startAnimation(animation)
        }

    }//onCreate
}//
