package com.example.shounak.bargainingbot.ui.intro.splashscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.intro.IntroSlideActivity
import com.example.shounak.bargainingbot.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class SplashActivity : AppCompatActivity() {

    private var onCreateCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        onCreateCounter++

        val auth = FirebaseAuth.getInstance()


        Timer().schedule(object : TimerTask() {
            override fun run() {

                runBlocking {
                    withContext(Dispatchers.Main) {
                        if (auth.currentUser == null && onCreateCounter <= 1) {
                            intent = Intent(this@SplashActivity, IntroSlideActivity::class.java)
                            startActivity(
                                intent,
                                ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity).toBundle()
                            )
                            finish()
                        } else {
                            intent = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }

        }, 1000)
    }
}
