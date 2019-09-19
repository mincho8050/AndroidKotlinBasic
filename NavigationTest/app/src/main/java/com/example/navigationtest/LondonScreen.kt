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
class LondonScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_london_screen, container, false)

        view.findViewById<Button>(R.id.to_paris_from_london).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_global_paris_screen)
        }
        view.findViewById<Button>(R.id.to_italy_from_london).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_global_italy_screen)
        }

        //이전에 이걸 안했더니 화면이 안보인것 같다.
        return view
    }


}
