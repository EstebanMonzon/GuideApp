package com.ort.guideapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ort.guideapp.databinding.ActivityMainBinding

/*___________________________________ AppCompatActivity ___________________________________*/
class MainActivity : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuath
        var firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
    }
    private fun checkUser(){

    }
}