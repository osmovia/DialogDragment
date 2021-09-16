package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragment.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentLogin, LoginFragment())
            .commitAllowingStateLoss()
    }
}