package com.bryanbeale.ulrichmetronome

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener

import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnClickListener {

    var playing = false

    override fun onClick(p0: View?) {

        if (p0?.id == fab.id){
            if (playing) {
                playing = true
                fab.setImageResource(android.R.drawable.ic_media_pause)
            }
            else{
                fab.setImageResource(android.R.drawable.ic_media_play)
                playing = false
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", this).show()
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun metronome(bpm: Int, bpb: Int, maxBeats: Int = Int.MAX_VALUE) {
        val delay = 60_000L / bpm
        var beats = 0
        do {
            Thread.sleep(delay)
            if (beats % bpb == 0) print("\nTICK ")
            else print("tick ")
            beats++
        }
        while (beats < maxBeats)
        println()
    }
}
