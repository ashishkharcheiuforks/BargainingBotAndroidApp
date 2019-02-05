package com.example.shounak.bargainingbot.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_slide_intro.*

/**
 * Created by Shounak on 29-Jan-19
 */
class IntroSlideActivity : AppCompatActivity() {

    /*
    TODO: Add skip button. Fix scrolling back from last slide keeps next button as next activity button
     */


    private var mDotLayout: LinearLayout? = null

//    private lateinit var mDots: Array<TextView?>


    lateinit var mNextBtn: Button
    lateinit var mBackBtn: Button

    private var mCurrentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_intro)


        val mSlideViewPager = slideViewPager
        val sliderAdapter = SliderAdapter(this)
        mSlideViewPager.adapter = sliderAdapter
        mSlideViewPager.addOnPageChangeListener(viewListener)
        mSlideViewPager.setPageTransformer(true, ZoomOutPageTransformer())


//        mSlideViewPager = findViewById(R.id.slideViewPager)
//        mDotLayout = dotsLayout

        mNextBtn = nextBtn
        mBackBtn = prevBtn

//        sliderAdapter = SliderAdapter(this)

//        mSlideViewPager!!.adapter = sliderAdapter

//        addDotsIndicator(0)


        //OnClickListeners

        mNextBtn.setOnClickListener {

            if (mCurrentPage < 2) {
                mSlideViewPager.currentItem = mCurrentPage + 1
            } else {

               callLoginActivity()

            }

        }

        mBackBtn.setOnClickListener { mSlideViewPager.currentItem = mCurrentPage - 1 }

        skip_text_view.setOnClickListener {
           callLoginActivity()
        }

    }

    private fun callLoginActivity() {
        val intent = Intent(this@IntroSlideActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {

        }

        override fun onPageSelected(i: Int) {
//            addDotsIndicator(i)

            mCurrentPage = i

            when (i) {
                0 -> {
                    mNextBtn.isEnabled = true
                    mBackBtn.isEnabled = false
                    mBackBtn.visibility = View.INVISIBLE

                    mNextBtn.text = "Next"
                    mBackBtn.text = ""
                }
                3 - 1 -> {
                    mNextBtn.isEnabled = true
                    mBackBtn.isEnabled = true
                    mBackBtn.visibility = View.VISIBLE

                    mNextBtn.text = "Let's Go"
                    mBackBtn.text = "Back"


                }
                else -> {
                    mNextBtn.isEnabled = true
                    mBackBtn.isEnabled = true
                    mBackBtn.visibility = View.VISIBLE

                    mNextBtn.text = "Next"
                    mBackBtn.text = "Back"


                }
            }

        }

        override fun onPageScrollStateChanged(i: Int) {

        }
    }


//    @RequiresApi(Build.VERSION_CODES.N)
//    fun addDotsIndicator(position: Int) {
//        mDots = arrayOfNulls(3)
//        mDotLayout!!.removeAllViews()
//
//        for (i in mDots.indices) {
//
//            mDots[i] = TextView(this)
//            mDots[i]?.text = Html.fromHtml("&#8266", Html.FROM_HTML_MODE_COMPACT)
//            mDots[i]?.textSize = 35f
//            mDots[i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryDarkColor))
//
//            mDotLayout!!.addView(mDots[i])
//        }
//
//
//        if (mDots.isNotEmpty()) {
//            mDots[position]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.primaryColor))
//        }
//    }
//
//    companion object {
//
//        val FROM_HTML_MODE_LEGACY = 0
//
//        fun fromHtml(html: String): Spanned {
//            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
//            } else {
//                Html.fromHtml(html)
//            }
//        }
//    }


}

