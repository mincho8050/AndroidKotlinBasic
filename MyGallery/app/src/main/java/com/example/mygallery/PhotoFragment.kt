package com.example.mygallery


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo.*

/*
    [프래그먼트에 사진 표시하기]
    -1) 클래스 선언 밖에 const 키워드를 사용하여 상수를 정의하면 컴파일 시간에
    결정되는 상수가 되고 이 파일 내에서 어디서든 사용할 수 있다.
    컴파일 시간 상수의 초기화는 String 또는 프리미티브형(Int, Long, Double등 기본형)으로만 초기화 할 수 있다.
    -2) newInstance() 메서들 이용하여 프래그먼트를 생성할 수 있고 인자로 uri값을 전달.
    이 값은 Bundle 객체에 ARG_URI 키로 저장되고 arguments 프로퍼티에 저장
    -3) 프래그먼트가 생성되면 onCreate() 메서드가 호출되고 ARG_URI 키에 저장된 uri값을 얻어 변수에 저장
    -4) onCreateView() 메서드에서는 프래그먼트에 표시될 뷰를 생성. 
    액티비티가 아닌 곳에서 레이아웃 리소스를 가져오렴 LayoutInflater 객체의 inflate() 메서드를 사용.
    R.layout.fragment_photo 레이아웃 파일을 가지고 와서 반환. 기본 코드를 그대로 두면 됨
    -5) Glide.with(this)로 사용 준비를 하고 load() 메서드에 uri값을 인자로 주고 해당 이미지를 부드럽게 로딩한다.
    이미지가 로딩되면 into() 메서드로 imageView에 표시.

    --> 이미지를 빠르고 부드럽게 로딩하고 메모리 관리까지 자동으로 하고 싶다면 Glide를 사용하기.
 */

private const val ARG_URI = "uri" //-1


class PhotoFragment : Fragment() {

    private var uri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {//-3
            uri = it.getString(ARG_URI)
        }
    }//onCreate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //-4
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(uri).into(imageView) //-5
    }//onViewCreated

    //-2
    companion object {
        @JvmStatic
        fun newInstance(uri:String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URI, uri)
                }
            }
    }

}//
