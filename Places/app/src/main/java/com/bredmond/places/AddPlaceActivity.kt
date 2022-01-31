package com.bredmond.places

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar


class AddPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        //        This is setting up a Toolbar in this Activity
        setSupportActionBar(findViewById(R.id.toolbar_add_place))
        val toolbarAddPlace: Toolbar = findViewById(R.id.toolbar_add_place)
        //        This creates a back button to go to the HOME ACTIVITY
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAddPlace.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}