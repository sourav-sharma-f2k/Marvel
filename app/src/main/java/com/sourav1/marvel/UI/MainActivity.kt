package com.sourav1.marvel.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sourav1.marvel.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, LoadCharacters())
                commit()
            }
        }
    }
}