package com.marcus.alex.balls

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marcus.alex.balls.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.addFab.setOnClickListener {
            binding.ballView.addBall()
        }

        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                Log.i(javaClass.toString(), "NightModeON")
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                Log.i(javaClass.toString(), "NightModeOFF")
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                Log.i(javaClass.toString(), "NightModeUNKOWN")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_reset -> {
                binding.ballView.reset()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}