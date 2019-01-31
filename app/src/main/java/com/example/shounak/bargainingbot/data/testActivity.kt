package com.example.shounak.bargainingbot.data

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.login.LoginActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_test.*

class testActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        window.exitTransition = Fade()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        logoutButton.setOnClickListener{
            LoginManager.getInstance().logOut()
            FirebaseAuth.getInstance().signOut()
            mGoogleSignInClient.signOut()

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent , ActivityOptions.makeSceneTransitionAnimation(this@testActivity).toBundle())
        }

    }
}
