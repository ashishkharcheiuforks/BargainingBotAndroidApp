package com.example.shounak.bargainingbot.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shounak.bargainingbot.R
import kotlinx.android.synthetic.main.activity_slide_intro.*

/**
 * Created by Shounak on 29-Jan-19
 */
class IntroSlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_intro)

        val viewPager = slideViewPager
        val customAdapter = IntroSlideAdapter(supportFragmentManager)
        viewPager.adapter = customAdapter
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())
    }
}