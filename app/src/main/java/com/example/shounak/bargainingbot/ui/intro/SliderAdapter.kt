package com.example.shounak.bargainingbot.ui.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.shounak.bargainingbot.R

/**
 * Adapter for intro slides
 */
class SliderAdapter(internal var context: Context) : PagerAdapter() {


    internal lateinit var layoutInflater: LayoutInflater

    //Arrays
    var slide_images = intArrayOf(

        R.drawable.main_logo, R.drawable.bartender, R.drawable.eat_icon
    )

    var slide_headings = arrayOf(

        "Welcome to", "", ""
    )

    var slide_descs = arrayOf(

        "Here, you will meet our Barista in this app. He'll put your negotiation skills to test. Make your offers to our Barista for your favourite drinks & convince him to sell at the lowest price.",
        "The more number of times you visit our place, the more better offers our Barista will provide you in the future",
        "Barista's offer deals are different for everybody. Find yourself the best deals by being his loyal customer."
    )

    override fun getCount(): Int {
        return slide_headings.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(LayoutInflater::class.java)
        val view = layoutInflater.inflate(R.layout.slide_layout, container, false)

        val slideImageView = view.findViewById(R.id.slide_image) as ImageView
        val slideHeading = view.findViewById(R.id.slide_heading) as TextView
        val slideDescription = view.findViewById(R.id.slide_desc) as TextView

        slideImageView.setImageResource(slide_images[position])
        slideHeading.text = slide_headings[position]
        slideDescription.text = slide_descs[position]

        container.addView(view)

        return view

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as ConstraintLayout)
    }
}