package com.bryanbeale.ulrichmetronome

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by bryanbeale on 1/28/18.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment.newInstance(), "Settings").commit()

    }


}