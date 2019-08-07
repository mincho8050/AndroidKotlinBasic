package com.example.mygallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/*
    -1) 어댑터가 프래그먼트의 목록을 리스트로 가지도록 한다. 이 목록은 4)updateFragment() 메서드를 사용해 외부에 추가할 수 있다.
    -2) getItem() 메서드에는 position 위치에 어떤 프래그먼트를 표시할지 정의
    -3) getCount() 메서드에는 아이템 개수 정의
 */

class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    //뷰페이저가 표시할 프래그먼트 목록-1
    private  val items = ArrayList<Fragment>()

    //position 위치의 프래그먼트-2
    override fun getItem(position: Int): Fragment {
        return items[position]
    }//getItem

    //아이템 갯수-3
    override fun getCount(): Int {
        return items.size
    }//getCount

    //아이템 갱신-4
    fun updateFragment(items : List<Fragment>){
        this.items.addAll(items)
    }//updateFragment


}//