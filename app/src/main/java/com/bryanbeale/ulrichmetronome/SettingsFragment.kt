package com.bryanbeale.ulrichmetronome

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat


/**
 * Created by bryanbeale on 1/27/18.
 */

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{

    companion object {

        fun newInstance(): SettingsFragment{
            return SettingsFragment()
        }

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

    }


    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        var pref = findPreference(p1) as ListPreference

        if (p1.equals("list_preference_1")){
            pref.title = pref.entries[pref.findIndexOfValue(pref.value)]
        }

        if (p1.equals("list_preference_2")){
            pref.title = pref.entries[pref.findIndexOfValue(pref.value)]
        }

        context.getSharedPreferences(p1, Context.MODE_PRIVATE).edit().putString(p1, pref.value).apply()

    }
}