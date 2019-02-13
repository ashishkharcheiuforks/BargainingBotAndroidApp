package com.example.shounak.bargainingbot.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.ui.base.ScopedActivity
import com.example.shounak.bargainingbot.ui.login.LoginActivity
import com.example.shounak.bargainingbot.ui.main.bot.BotFragmentDirections
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : ScopedActivity(), NavigationView.OnNavigationItemSelectedListener , KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: MainActivityViewModelFactory by instance()


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shounak.bargainingbot.R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        val pref = PreferenceProvider.getPrefrences(this)
        pref.edit().putString(PreferenceProvider.USER_ID, FirebaseAuth.getInstance().currentUser?.uid).apply()

        setupNavigation()

        bindUI(viewModel)

        logOutOnClickListener()
    }

    private fun logOutOnClickListener() {

        logout_button.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("LOG OUT?")
                .setIcon(R.drawable.ic_error_outline_e80000_24dp)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    LoginManager.getInstance().logOut()
                    GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
                .show()

        }
    }

    private fun bindUI(viewModel: MainActivityViewModel) {


        launch {
            val currentUser = viewModel.user.await()

            currentUser.observe(this@MainActivity, Observer {
                if (it != null) {

                    val username = "${it.firstName} ${it.lastName}"

                    drawer_header_user_name.text = username

                    drawer_header_user_email.text = it.email

                    Glide.with(this@MainActivity)
                        .load(it.photoUrl)
                        .apply(RequestOptions().circleCrop())
                        .into(drawer_header_user_image)

                }
            })

        }

    }


    private fun setupNavigation() {

        toolbar = findViewById(com.example.shounak.bargainingbot.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.drawer_nav_view)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navigationView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

        navigationView.menu.getItem(0).isChecked = true

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

     override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true

        drawerLayout.closeDrawers()

        val id = menuItem.itemId
        when (id) {

            R.id.nav_drawer_menu_home -> {
            }

            R.id.orders_fragment -> {

                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment).navigate(BotFragmentDirections.actionToOrders())

            }

            R.id.nav_drawer_menu_concept -> {
            }

            R.id.nav_drawer_menu_about -> {
            }

        }

        return true

    }

}
