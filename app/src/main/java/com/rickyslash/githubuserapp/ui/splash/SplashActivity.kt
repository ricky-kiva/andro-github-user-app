package com.rickyslash.githubuserapp.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rickyslash.githubuserapp.R
import com.rickyslash.githubuserapp.database.SettingPreferences
import com.rickyslash.githubuserapp.helper.SettingsViewModelFactory
import com.rickyslash.githubuserapp.ui.main.MainActivity
import com.rickyslash.githubuserapp.ui.settings.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getThemePreference()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val splashTimer = object : CountDownTimer(1500, 1000) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                val moveMainActivityIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(moveMainActivityIntent)
                finish()
            }
        }

        splashTimer.start()
    }

    private fun getThemePreference() {
        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}