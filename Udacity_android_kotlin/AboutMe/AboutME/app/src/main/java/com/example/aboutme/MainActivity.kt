package com.example.aboutme

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.aboutme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName: MyName = MyName("Aleks Haecky")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) //activity_main 레이아웃을 받는다.
        //Data binding 방법
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myName=myName

        //done_button을 누를시!
        /*done_button.setOnClickListener {
            addNickname(it)
        }*/
        //Data binding 방법
        binding.doneButton.setOnClickListener {
            addNickname(it)
        }
    }//onCreate

    //nickname_edit에 적고 done_button을 누르면 nickname_text로 바뀜
    private fun addNickname(view: View){
        /*nickname_text.text=nickname_edit.text
        nickname_edit.visibility=View.GONE
        view.visibility=View.GONE
        nickname_text.visibility=View.VISIBLE
        //nickname_edit에 내용을 적으면 nickname_edit은 사라지고
        //그 내용을 담은 nickname_text는 보인다.*/

        //Data binding 방법
        binding.apply {
            //nicknameText.text=binding.nicknameEdit.text
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility=View.GONE
            doneButton.visibility=View.GONE
            nicknameText.visibility=View.VISIBLE
        }

        //입력이 완료된 후 키보드를 숨기기 위한 코드
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }//addNickname

}
