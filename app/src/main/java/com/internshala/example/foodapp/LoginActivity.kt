package com.internshala.example.foodapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity :AppCompatActivity(){//acts as mainActivity.kt and layout file is activity_main but not activity_login
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var navigation: NavigationView
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        frameLayout=findViewById(R.id.Frame)
        toolbar=findViewById(R.id.ToolBar)
        navigation=findViewById(R.id.navigationView)
        setUpToolbar()

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@LoginActivity,drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportFragmentManager.beginTransaction().replace(R.id.Frame,DashboardFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title="Dashboard"

        val user_id=intent.getStringExtra("user_id")
        val name=intent.getStringExtra("name")
        val phoneNumber=intent.getStringExtra("mobile_number")
        val email=intent.getStringExtra("email")
        val address=intent.getStringExtra("address")

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null)
                previousMenuItem?.isChecked=false
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId)
            {
                R.id.dashboard->{
                    supportFragmentManager.beginTransaction().replace(R.id.Frame,DashboardFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Dashboard"
                }
                R.id.profile->{

                    //val profileFragment=ProfileFragment.newInstance(name,)

                    supportFragmentManager.beginTransaction().replace(R.id.Frame,ProfileFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"



                }
                R.id.favourites->{
                    supportFragmentManager.beginTransaction().replace(R.id.Frame,FavouritesFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favourites"
                }
                R.id.faq->{
                    supportFragmentManager.beginTransaction().replace(R.id.Frame,FaqFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="FAQs"
                }

                R.id.orderHistory->{
                    supportFragmentManager.beginTransaction().replace(R.id.Frame,OrderHistory()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Order History"
                }
                R.id.logout->{supportFragmentManager.beginTransaction().replace(R.id.Frame,DashboardFragment()).commit()
                    drawerLayout.closeDrawers()//Log out fragment has to be changed////////////
                    supportActionBar?.title="logout"
                }

            }
            return@setNavigationItemSelectedListener true
        }


    }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="ToolBar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        if(id==android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.Frame)
        when(frag){
            !is DashboardFragment -> run{
                supportFragmentManager.beginTransaction().replace(R.id.Frame,DashboardFragment()).addToBackStack("Dashboard").commit()
                supportActionBar?.title="Dashboard"
                drawerLayout.closeDrawers()
            }
            else -> {super.onBackPressed()}
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}