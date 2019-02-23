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
import com.example.shounak.bargainingbot.internal.DrawerLocker
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
import kotlinx.android.synthetic.main.select_table_popup_window.view.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : ScopedActivity(), NavigationView.OnNavigationItemSelectedListener, KodeinAware,
    DrawerLocker {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: MainActivityViewModelFactory by instance()


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shounak.bargainingbot.R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        val pref = PreferenceProvider.getPrefrences(this)
        pref.edit().apply {
            putString(PreferenceProvider.USER_ID, FirebaseAuth.getInstance().currentUser?.uid)
        }.apply()

        viewModel.user

        showSelectTableDialog()

        viewModel.setCartClearedFalse()

        setupNavigation()

        bindUI(viewModel)


        logOutOnClickListener()
    }

    private fun showSelectTableDialog() {

        val tableNumber = PreferenceProvider.getPrefrences(this).getInt(PreferenceProvider.TABLE_NUMBER, 0)
        if (tableNumber == 0) {
            val view = layoutInflater.inflate(R.layout.select_table_popup_window, null)
            view.select_table_number_picker.apply {
                minValue = 1
                maxValue = 30
            }

            val alertDialog = AlertDialog
                .Builder(this)
                .setView(view)
                .setCancelable(false)
                .show()

            view.select_table_button.setOnClickListener {
                PreferenceProvider.getPrefrences(this)
                    .edit()
                    .putInt(PreferenceProvider.TABLE_NUMBER, view.select_table_number_picker.value)
                    .apply()

                viewModel.isTableSelected.value = true

                alertDialog.dismiss()
            }
        }
    }

    private fun logOutOnClickListener() {

        logout_button.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("LOG OUT?")
                .setIcon(R.drawable.ic_error_outline_e80000_24dp)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    runBlocking {
                        logout()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
                .show()

        }
    }

    private suspend fun logout() {
        viewModel.getOrders().observe(this, Observer { list ->
            run {
                viewModel.isDrinksLoadingCompleted.observe(this, Observer {
                    if (it == true) {
                        if (list.isEmpty()) {
                            clearData()
                            callLogOutAndNavigate()
                        } else {
                            callLogOutAndNavigate()
                        }
                    }
                })
            }
        })
    }

    private fun callLogOutAndNavigate() {
        LoginManager.getInstance().logOut()
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        FirebaseAuth.getInstance().signOut()
        PreferenceProvider.getPrefrences(this).edit().putInt(PreferenceProvider.TABLE_NUMBER, 0).apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearData() {
        runBlocking {
            val prefs = PreferenceProvider.getPrefrences(this@MainActivity)
            val uid = prefs.getString(PreferenceProvider.USER_ID, null)
            if (uid != null) {
                runBlocking { viewModel.clearData(uid) }
            }

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

                    drawer_header_user_is_regular.text = when {
                        it.regular -> "Buddy"

                        else -> "Stranger"
                    }

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

                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(BotFragmentDirections.actionBotFragmentSelf())

            }

            R.id.orders_fragment -> {

                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(BotFragmentDirections.actionToOrders())

            }

            R.id.nav_drawer_menu_concept -> {

                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(BotFragmentDirections.actionToIntroSlideActivity())

            }

            R.id.nav_drawer_menu_about -> {

                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(R.id.aboutFragment)

            }

        }

        return true

    }

    override fun setDrawerEnabled() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun setDrawerDisabled() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
}
