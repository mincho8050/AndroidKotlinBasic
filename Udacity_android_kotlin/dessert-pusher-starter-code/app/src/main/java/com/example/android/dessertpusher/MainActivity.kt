/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.dessertpusher

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.android.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber

const val KEY_REVENUE="key_revenue"

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private var revenue = 0
    private var dessertsSold = 0
    private lateinit var dessertTimer: DessertTimer

    // 모든 보기 포함
    private lateinit var binding: ActivityMainBinding

    /** Dessert Data **/

    /**
     * 디저트를 나타내는 간단한 데이터 클래스. 연결된 리소스 ID 정수 포함
     * 이미지, 판매되는 가격, 그리고 시작 프로덕션Amount를 통해 제품 출시 시기를 결정
     * 디저트가 생산되기 시작한다.
     */
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    // 모든 디저트 목록 작성, 생산 시작 시점 순서대로
    private val allDesserts = listOf(
            Dessert(R.drawable.cupcake, 5, 0),
            Dessert(R.drawable.donut, 10, 5),
            Dessert(R.drawable.eclair, 15, 20),
            Dessert(R.drawable.froyo, 30, 50),
            Dessert(R.drawable.gingerbread, 50, 100),
            Dessert(R.drawable.honeycomb, 100, 200),
            Dessert(R.drawable.icecreamsandwich, 500, 500),
            Dessert(R.drawable.jellybean, 1000, 1000),
            Dessert(R.drawable.kitkat, 2000, 2000),
            Dessert(R.drawable.lollipop, 3000, 4000),
            Dessert(R.drawable.marshmallow, 4000, 8000),
            Dessert(R.drawable.nougat, 5000, 16000),
            Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1)여기에 정보 수준 로그 문 추가
        Timber.i("onCreate called")
        //dessertsSold=0
        //>여기에있으면 앱을 나갔다 들어와도 카운트+돈이 계속 쌓인다
        /*
           앱을 실행하면 Logcat에
           2019-08-22 19:55:36.078 12192-12192/? I/MainActivity: onCreate called 이렇게 됨.
         */
        // Data Binding을 사용하여 뷰 참조
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }
        
        //DessertTimer 만들기
        dessertTimer= DessertTimer(this.lifecycle) //'This' MainActivity의 라이프사이클을 전달하여 관찰

        //bundle savedInstanceState가 null인 경우 여기를 확인하십시오.
        if(savedInstanceState!=null){
            revenue=savedInstanceState.getInt(KEY_REVENUE)
        }


        // TextViews를 올바른 값으로 설정
        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // 올바른 디저트가 표시되는지 확인하십시오.
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    /**
     * 디저트를 클릭하면 점수를 업데이트하십시오. 아마도 새로운 디저트를 보여줄 것이다.
     */
    private fun onDessertClicked() {

        // Update the score
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    /**
     * 어떤 디저트를 보여줄지 결정하라.
     */
    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // 디저트 목록은 startProductionAmount에 따라 정렬된다. 디저트를 더 많이 팔면
            // startProductionAmount에 의해 결정한 대로 더 비싼 디저트를 생산하기 시작할 것이다.
            // "startProductionAmount"가 더 큰 디저트를 보는 즉시 중단해야 한다는 것을 알고 있다.
            // 판매한 금액보다.
            else break
        }

        // 새로운 디저트가 실제로 현재의 디저트와 다른 경우 이미지를 업데이트하십시오.
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }
    
    //이거 override하면 override fun onSaveInstanceState(outState: Bundle?)
    //->이렇게 되면 잘 안됨 ? 를 삭제해야함.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("key_revenue", revenue)
        Timber.i("onSaveInstanceState Called")
    }

    /**Lifecycle Method
     * Logcat에 앱을 실행하고 종료할 때마다 어떻게 표시되는지 확인.
     * **/
    //2) onStart 라이프사이클 메서드 재정의 및 정보 수준 로그 문 추가
    override fun onStart() {
        super.onStart()
        //dessertTimer.startTimer()
        //앱을 나갔다 들어오고 그림을 한번 클릭하면 그림과 Sold는 초기화 되는데, 금액은 여전하다
        Timber.i("onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        //dessertTimer.stopTimer()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

}
