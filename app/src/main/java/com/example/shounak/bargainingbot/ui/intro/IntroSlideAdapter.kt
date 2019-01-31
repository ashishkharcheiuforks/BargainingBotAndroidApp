package com.example.shounak.bargainingbot.ui.intro

class IntroSlideAdapter( fm : androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    override fun getItem(i: Int): androidx.fragment.app.Fragment {

        return IntroSlidePageFragment()
    }

    override fun getCount(): Int {
       return 3
    }

}
