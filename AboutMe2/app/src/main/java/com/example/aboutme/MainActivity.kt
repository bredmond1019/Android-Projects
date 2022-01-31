package com.example.aboutme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClickMe = findViewById<Button>(R.id.mybutton)
        val displayText = findViewById<TextView>(R.id.displayedText)
        var timesClicked = 0

        btnClickMe.setOnClickListener {
            timesClicked += 1
            displayText.text = timesClicked.toString()

        }
    }
}