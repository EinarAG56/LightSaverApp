package com.example.lightsaver.ui

import android.app.Activity
import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle


import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.lightsaver.BaseActivity
import com.example.lightsaver.R
import com.example.lightsaver.manager.WiFiReceiverManager
import com.example.lightsaver.manager.WiFiResponse
import com.example.lightsaver.manager.WiFiStatus
import com.example.lightsaver.util.IntentUtils

import com.example.lightsaver.databinding.ActivitySettingsBinding
import timber.log.Timber
import androidx.appcompat.widget.Toolbar

class LogActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.activity_logs)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.contentFrame, LogFragment.newInstance(), LOGS_FRAGMENT).commit()
        }
        resetInactivityTimer()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_logs
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val LOGS_FRAGMENT = "com.example.lightsaver.fragment.LOGS_FRAGMENT"
        fun start(activity: Activity) {
            activity.startActivity(newIntent(activity))
        }
        private fun newIntent(context: Context): Intent {
            return IntentUtils.newIntent(context, LogActivity::class.java)
        }
    }
}