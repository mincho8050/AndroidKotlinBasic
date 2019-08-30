/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.aboutme

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.aboutme.databinding.ActivityMainBinding


/*
    AboutMe 앱의 주요 활동. 이 앱은 다음을 보여 준다.
    * LinearLayout with TextViews, ImageView, Button, EditText, ScrollView
    * 스크롤보기 - 스크롤 가능한 텍스트 표시
    * EditText를 사용하여 사용자 입력.
    * EditText에서 텍스트를 검색하여 TextView로 설정하려면 버튼 처리기를 클릭하십시오.
    * 뷰의 가시성 상태 설정
    * MainActivity와 activity_main.xml 간의 데이터 바인딩. findViewById를 제거하는 방법,
    * 및 데이터 바인딩 개체를 사용하여 보기에 데이터를 표시하는 방법.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName: MyName = MyName("Aleks Haecky")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myName = myName

        binding.doneButton.setOnClickListener {
            addNickname(it)
        }

    }

    /*
     * 완료 단추에 대한 처리기를 누르십시오.
     * 데이터 바인딩을 사용하여 코드를 훨씬 더 읽기 쉽게 만드는 방법 시연
     * findViewById에 대한 호출을 제거하고 바인딩 개체에서 데이터를 변경함으로써.
     */
    private fun addNickname(view: View) {
        binding. apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
