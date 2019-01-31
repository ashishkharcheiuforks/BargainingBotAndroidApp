package com.example.shounak.bargainingbot.ui.intro.splashscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.transition.Fade
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.login.LoginActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.enterTransition = Fade()
        window.exitTransition = Fade()

        val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity)


        Timer().schedule(object : TimerTask() {
            override fun run() {

                intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent, options.toBundle())
                finish()

            }

        }, 1200)
    }
}
