package com.example.shounak.bargainingbot.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : MainActivityViewModelFactory by instance()


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shounak.bargainingbot.R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.setUserDataToRoom()

        setupNavigation()



    }





    private fun setupNavigation() {

        toolbar = findViewById(com.example.shounak.bargainingbot.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(com.example.shounak.bargainingbot.R.id.drawer_layout)

        navigationView = findViewById(com.example.shounak.bargainingbot.R.id.drawer_nav_view)

        navController = Navigation.findNavController(this, com.example.shounak.bargainingbot.R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navigationView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = false

        drawerLayout.closeDrawers()

//        val id = menuItem.itemId
//        when (id) {
//
//            com.example.shounak.bargainingbot.R.id.first -> navController.navigate(com.example.shounak.bargainingbot.R.id.firstFragment)
//
//            com.example.shounak.bargainingbot.R.id.second -> navController.navigate(com.example.shounak.bargainingbot.R.id.secondFragment)
//
//            com.example.shounak.bargainingbot.R.id.third -> navController.navigate(com.example.shounak.bargainingbot.R.id.thirdFragment)
//        }

        return true

    }

}
