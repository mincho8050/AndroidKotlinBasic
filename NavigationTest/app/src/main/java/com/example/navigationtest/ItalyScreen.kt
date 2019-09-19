package com.example.navigationtest


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 */
class ItalyScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /*
        * 이제 fragment에서 버튼을 눌렀을 때 이동하는 코드를 작성한다.
        * 버튼을 눌렀을 때 화면을 변경하고 싶으면 Navigation Controller 객체를 이용한다.
        * Controller는 Host에 보여지고 있는 View를 변경해주는 역할이다.
        *
        * 일단 Fragment 파일들을 열고 버튼 리스너를 정의한다.
        * Navigation.findNavController는 Controller를 가져오는 코드이고,
        * navigate는 정의된 action의 destination으로 이동하라는 의미이다.
        * */
        val view = inflater.inflate(R.layout.fragment_italy_screen, container, false)

        view.findViewById<Button>(R.id.to_london_from_italy).setOnClickListener {
            //Navigation.findNavController(view).navigate(R.id.action_italy_screen_to_london_screen)
            //>Local action을 사용했을때.
            Navigation.findNavController(view).navigate(R.id.action_global_london_screen)
            //>Global action을 생성했을 때
        }

        view.findViewById<Button>(R.id.to_paris_from_italy).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_global_paris_screen)
        }

        //이전에 이걸 안했더니 화면이 안보인것 같다.
        return view
    }


}
