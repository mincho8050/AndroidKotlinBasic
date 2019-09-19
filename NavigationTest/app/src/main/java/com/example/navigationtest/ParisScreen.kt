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
class ParisScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paris_screen, container, false)

        view.findViewById<Button>(R.id.to_italy_from_paris).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_global_italy_screen)
        }

        view.findViewById<Button>(R.id.to_london_from_paris).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_global_london_screen)
        }

        //이전에 이걸 안했더니 화면이 안보인것 같다.
        return view
    }


}
