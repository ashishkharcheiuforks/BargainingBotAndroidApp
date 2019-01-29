package com.example.shounak.bargainingbot.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shounak.bargainingbot.R
import kotlinx.android.synthetic.main.fragment_intro_slide_page.*


class IntroSlidePageFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_slide_page, container, false)

        val cardView = cardView
        val textView = textView3

        return view
    }


}
