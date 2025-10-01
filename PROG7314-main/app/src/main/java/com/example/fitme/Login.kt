package com.example.fitme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Navigate to Register activity:
        val btnRegister : Button = findViewById(R.id.btnRegisterNav)

        btnRegister.setOnClickListener()
        {
            val intent : Intent = Intent (this, Register::class.java)
            startActivity(intent)
        }

        //Login successful:
        val btnLogin : Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener()
        {
            val intent : Intent = Intent (this, Home::class.java)
            startActivity(intent)
        }
    }
}