package com.example.lightsaver.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.lightsaver.BaseActivity
import com.example.lightsaver.R
import com.example.lightsaver.manager.WiFiReceiverManager
import com.example.lightsaver.manager.WiFiResponse
import com.example.lightsaver.manager.WiFiStatus
import timber.log.Timber
import androidx.appcompat.widget.Toolbar
import com.example.lightsaver.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), TransmitFragment.OnFragmentInteractionListener,
        MainFragment.OnFragmentInteractionListener, ViewPager.OnPageChangeListener {

    private var connectionLiveData: ConnectionLiveData? = null
    private lateinit var pagerAdapter: PagerAdapter
    private var wiFiReceiverManager: WiFiReceiverManager? = null
    private var waitingForConnection: Boolean = false;
    private var wifiStatus: WiFiStatus? = null;
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        pagerAdapter = MainSlidePagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addOnPageChangeListener(this)
        binding.viewPager.setPagingEnabled(false)

        // Monitor network connection
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData?.observe(this, Observer { connected ->
            if(connected!! && waitingForConnection) {
                waitingForConnection = false;
                if(wifiStatus == WiFiStatus.CONNECTED) {
                    wifiConnected()
                } else if (wifiStatus == WiFiStatus.DISCONNECTED) {
                    wifiDisconnected()
                }
            } else if (!connected) {
                if(wifiStatus == WiFiStatus.CONNECTED) {
                    Toast.makeText(this@MainActivity, getString(R.string.error_network_disconnected), Toast.LENGTH_SHORT).show()
                    wifiDisconnected()
                }
            }
        })

        // Monitor WiFi connection
        wiFiReceiverManager = WiFiReceiverManager(application, lifecycle)
        wiFiReceiverManager!!.wifiResponse().observe(this, Observer {response -> processWifiResponse(response)})
    }

    @LayoutRes
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                SettingsActivity.start(this@MainActivity)
                true
            }
            R.id.action_logs -> {
                LogActivity.start(this@MainActivity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

    private fun processWifiResponse(response: WiFiResponse?) {
        wifiStatus = response?.status
        when (wifiStatus) {
            WiFiStatus.CONNECTED -> {
                if(connectionLiveData!!.hasNetworkConnection()) {
                    wifiConnected()
                } else {
                    waitingForConnection = true;
                }
            }
            WiFiStatus.CONNECTING -> {
                binding.progressbar.visibility = View.VISIBLE
            }
            WiFiStatus.DISCONNECTED -> {
                if(connectionLiveData!!.hasNetworkConnection()) {
                    wifiDisconnected()
                } else {
                    waitingForConnection = true;
                }
            }
            WiFiStatus.DISCONNECTING -> {
                binding.progressbar.visibility = View.VISIBLE
            }
            WiFiStatus.ERROR -> {
                val message = response!!.error?.message.toString()
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun disconnectFromWifi() {
        if(wifiStatus == WiFiStatus.CONNECTED) {
            wiFiReceiverManager?.disconnectFromWifi()
        }
    }

    override fun connectWifi() {
        try{
            if(wiFiReceiverManager != null && wifiStatus != WiFiStatus.CONNECTED && wifiStatus != WiFiStatus.CONNECTING ) {
                wiFiReceiverManager?.connectWifi(preferences.ssID()!!, preferences.password()!!)
            }
        } catch (e: NullPointerException) {
            Timber.e(e.message)
        }
    }

    override fun wifiConnected() {
        Timber.d("wifiConnected")
        binding.progressbar.visibility = View.INVISIBLE
        if(binding.viewPager.currentItem == 0) {
            binding.viewPager.currentItem = 1
        }
    }

    override fun wifiDisconnected() {
        Timber.d("wifiDisconnected")
        binding.progressbar.visibility = View.INVISIBLE
        if(binding.viewPager.currentItem != 0) {
            binding.viewPager.currentItem = 0
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    private inner class MainSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                1-> return MainFragment.newInstance()
                0 -> return TransmitFragment.newInstance()
               // 0 -> return LightFragment.newInstance()
                else -> return MainFragment.newInstance()
            }
        }
        override fun getCount(): Int {
            return 2
        }
    }
}