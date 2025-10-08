package com.example.fitme

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Navigate to Settings:
        val btnSettingsNav : ImageButton = findViewById(R.id.btnSettingsNav)

        btnSettingsNav.setOnClickListener()
        {
            val intent : Intent = Intent (this, Settings::class.java)
            startActivity(intent)
        }

        //Bottom Navigation View:
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_progress -> {
                    val intent = Intent(this, Progress::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_camera -> {
                    val intent = Intent(this, Camera::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_search -> {
                    val intent = Intent(this, AddIntake::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    //No action needed already already at Profile
                    true
                }
                else -> false
            }
        }

        bottomNavigation.selectedItemId = R.id.nav_profile
    }
}