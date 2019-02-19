package com.example.shounak.bargainingbot.ui.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.main.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*


private const val RC_SIGN_IN: Int = 1
private lateinit var auth: FirebaseAuth

//TODO: Show alert if user email exists with different signin account.

class LoginActivity : AppCompatActivity(), View.OnClickListener, KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: LoginViewModelFactory by instance()


    private lateinit var viewModel: LoginViewModel
    private val TAG = "MainActivity"
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        //OnClickListener
        google_signin_button.setOnClickListener(this)


        //Facebook Sign In
        callbackManager = CallbackManager.Factory.create()

        //OnClickListener
        facebook_signin_button.setOnClickListener(this)

        //Firebase Auth instance
        auth = FirebaseAuth.getInstance()


    }

    override fun onStart() {
        super.onStart()

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val currentUser = auth.currentUser

        updateUI(currentUser)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Google Login Result
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }

            } catch (e: ApiException) {
                Log.e(TAG, "Google SignIn Failed", e)
            }
        }

        //Facebook Login Callback
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }

    override fun onClick(v: View?) {

        //OnClickListeners
        when (v?.id) {

            R.id.google_signin_button -> {
                googleSignIn()
            }

            R.id.facebook_signin_button -> {
                facebookSignIn()
            }
        }
    }


    //Exchange Google Credentials with Firebase Auth
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
//                    updateData(user)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(login_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
    }

    //Exchange Facebook Credentials with Firebase Auth
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
//                    updateData(user)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                   //TODO: Background color wrong
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_error_outline_e80000_24dp)
                        .setTitle("LOGIN FAILED.")
                        .setMessage("This email may be associated with a different account.")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        })
                        .show()
                    updateUI(null)
                }

                // ...
            }
    }

    //Google SignIn
    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //Facebook SignIn
    private fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
            }
        })
    }

    //Call next activity
    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {

        }
    }



}
