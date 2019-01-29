package com.example.shounak.bargainingbot.intro

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class IntroSlideAdapter( fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {

        return IntroSlidePageFragment()
    }

    override fun getCount(): Int {
       return 3
    }

}
