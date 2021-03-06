package com.bryanbeale.ulrichmetronome

import android.content.Intent
import android.content.res.TypedArray
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    lateinit var mAdView : AdView

    var mp = MediaPlayer()
    var mp2 = MediaPlayer()

    var playing = false

    var MIN = 30
    var MAX = 250
    var STEP = 1
    var RES_RAW = "res/raw/"
    var MP3 = ".mp3"
    var WAV = ".wav"
    var AIFF = ".aiff"
    var ThreadBoi = Thread()
    var BPB = arrayOf("2", "4", "6", "8", "16")
    var bpb = BPB[0]

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {

        super.onPause()

        playing = false

        fab.setImageResource(android.R.drawable.ic_media_play)

    }

    override fun onClick(p0: View?) {

        if (p0?.id == fab.id){
            if (!playing) {
                fab.setImageResource(android.R.drawable.ic_media_pause)
                playing = true
            }
            else{
                fab.setImageResource(android.R.drawable.ic_media_play)
                playing = false
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        setContentView(R.layout.content_main)

        spinner!!.onItemSelectedListener = this

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, BPB)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        setSupportActionBar(toolbar)

        fab.setOnClickListener(this)

        seekBar!!.max = (MAX - MIN)/STEP

        seekBar!!.setOnSeekBarChangeListener(this)

        spinner!!.adapter = aa
        spinner.setSelection(0)

        setupSounds()

        ThreadBoi = Thread(Runnable { metronome() })
        ThreadBoi.start()

        MobileAds.initialize(this, "ca-app-pub-7844185607942332~4216511020")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode : Int) {
               Log.wtf("AdMob", "Ad failed " + errorCode)

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
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
            R.id.action_settings -> openPreferenceFragment()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1) {

            setupSounds()
        }
    }

    private fun setupSounds() {
        val prefs = this.getSharedPreferences("com.bryanbeale.ulrichmetronome_preferences", MODE_PRIVATE)
        mp.reset()
        mp2.reset()

        mp = MediaPlayer.create(this, resources.getIdentifier(prefs.getString("list_preference_1", "").replace(RES_RAW, "").replace(WAV, "").replace(AIFF, "").replace(MP3, ""), "raw", packageName))
        mp2 = MediaPlayer.create(this, resources.getIdentifier(prefs.getString("list_preference_2", "").replace(RES_RAW, "").replace(WAV, "").replace(AIFF, "").replace(MP3, ""), "raw", packageName))
    }

    private fun openPreferenceFragment(): Boolean {

        startActivityForResult(Intent(this, SettingsActivity::class.java), 1)
        return true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        bpb = BPB[0]
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        bpb = BPB[p2]
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        var value  = MIN + (p0?.progress!! * STEP)

        textView.text = "$value BPM"
    }

    /**
     * bpm = beats per minute
     * bpb = beats per bar
     * maxBeats = the amount of beats to go through
     */
    private fun metronome(maxBeats: Int = Int.MAX_VALUE) {

        var beats = 0

        do {

            if (!playing){
                continue
            }

            var bpm = MIN + (seekBar?.progress!! * STEP)
            var bpb = this.bpb.toInt()
            var r = Random()
            var delay = 60_000L / bpm

            if (r.nextInt(2000) % 2 == 1){
                delay += r.nextInt(200)
            }

            if (beats % bpb == 0) {
                mp.setVolume(1f, 1f)
                mp.seekTo(0)
                mp.start()
                Log.wtf("Metronome", "TICK ")
            }
            else{
                mp2.setVolume(1f, 1f)
                mp2.seekTo(0)
                mp2.start()
                Log.wtf("Metronome" ,"tick ")
            }
            Thread.sleep(delay)
            beats++
        }
        while (beats < maxBeats)
    }
}

