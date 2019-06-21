package com.github.rogeryk.charity_android.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.github.rogeryk.charity_android.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.home_fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_news -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.news_fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.search_fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.my_fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RequestCodes.Login.ordinal -> {
                if (resultCode != Activity.RESULT_OK) {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(R.id.home_fragment)
                }
            }
        }
    }
}
