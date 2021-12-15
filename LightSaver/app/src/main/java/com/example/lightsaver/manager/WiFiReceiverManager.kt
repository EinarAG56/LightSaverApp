package com.example.lightsaver.manager

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.*
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import androidx.core.content.ContextCompat
import android.text.TextUtils
import androidx.annotation.RequiresApi

import timber.log.Timber
import com.example.lightsaver.viewmodel.SnackbarMessage
import com.example.lightsaver.viewmodel.ToastMessage

/*
https://gist.github.com/JosiasSena/100de74192ca3024da8494c1ca428294
 */
class WiFiReceiverManager(private val application: Application, lifecycle: Lifecycle) : LifecycleObserver {

    private val wifiManager: WifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val toastText = ToastMessage()
    private val snackbarText = SnackbarMessage()
    private val wifiResponse = MutableLiveData<WiFiResponse>()

    fun wifiResponse(): MutableLiveData<WiFiResponse> {
        return wifiResponse
    }

    fun getToastMessage(): ToastMessage {
        return toastText
    }

    fun getSnackbarMessage(): SnackbarMessage {
        return snackbarText
    }

    init {
        lifecycle.addObserver(this)
    }

    private fun showSnackbarMessage(message: Int?) {
        snackbarText.value = message
    }

    private fun showToastMessage(message: String?) {
        toastText.value = message
    }

    private val intentFilterForWifiConnectionReceiver: IntentFilter
        get() {
            val randomIntentFilter = IntentFilter(WIFI_STATE_CHANGED_ACTION)
            randomIntentFilter.addAction(NETWORK_STATE_CHANGED_ACTION)
            randomIntentFilter.addAction(SUPPLICANT_STATE_CHANGED_ACTION)
            return randomIntentFilter
        }

    private val wifiConnectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            //Timber.d("onReceive() called with: intent = [$intent]")
            val action = intent.action
            if (!TextUtils.isEmpty(action)) {
                Timber.d("action: " + action)
                when (action) {
                    WIFI_STATE_CHANGED_ACTION,
                    SUPPLICANT_STATE_CHANGED_ACTION -> {
                        val wifiInfo = wifiManager.connectionInfo
                        var wirelessNetworkName = wifiInfo.ssid
                        wirelessNetworkName = wirelessNetworkName.replace("\"", "");
                        Timber.d("WiFi Info current SSID " + wirelessNetworkName)
                        Timber.d("WiFi Info Hidden " + wifiInfo.hiddenSSID )
                        Timber.d("WiFi Connect To networkSSID " + networkSSID )
                        Timber.d("WiFi previous NetworkSSID " + previousNetworkSSID )
                        Timber.d("WiFi Names Equal " + (networkSSID == wirelessNetworkName))
                        if(networkSSID == wirelessNetworkName && !disconnecting) {
                            Timber.d("WiFi connected to " + networkSSID)
                            wifiResponse.value = WiFiResponse.connected()
                        } else if(networkSSID != wirelessNetworkName && disconnecting) {
                            wifiResponse.value = WiFiResponse.disconnected()
                        }
                    }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun registerYourReceiver() {
        application.registerReceiver(wifiConnectionReceiver, intentFilterForWifiConnectionReceiver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun unRegisterYourReceiver() {
        try {
            application.unregisterReceiver(wifiConnectionReceiver)
        } catch (e : IllegalArgumentException) {
            Timber.e(e.message)
        }
    }


    @SuppressLint("MissingPermission")
    private fun getNetworkId(networkSSID: String): Int {

        val list = wifiManager.configuredNetworks
        for (i in list) {
            if (i.SSID != null && i.SSID == "\"" + networkSSID + "\"") {
                return i.networkId
            }
        }
        return -1
    }
    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun connectWifi(ssid: String, password: String) {

        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(password)) {
            Timber.d("onReceive: cannot use connection without passing in a proper wifi SSID and password.")
            return
        }

        networkSSID = ssid
        networkPassword = password

        wifiResponse.value = WiFiResponse.connecting()
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }

        val wifiInfo = wifiManager.connectionInfo
        if(wifiInfo.ssid == networkSSID) {
            wifiResponse.value = WiFiResponse.connected()
            return
        }
        previousNetworkSSID = wifiInfo.ssid
        disconnecting = false
        connectToWifi()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectToWifi() {
        Timber.d("WiFi connectToWifi")
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsid(networkSSID)
            .setWpa2Passphrase(networkPassword)
            .setIsHiddenSsid(true)
            .build()

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(specifier)
            .build()

        /*val wifiNetworkSpecifier = WifiNetworkSuggestion.Builder()
            .setSsid(networkSSID)
            .setWpa2Passphrase(networkPassword)
            .setIsHiddenSsid(true)
            .build()

        val conf = WifiConfiguration()
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.preSharedKey = String.format("\"%s\"", networkPassword);
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.hiddenSSID = true;

        networkId = wifiManager.addNetworkSuggestions()
        if(networkId == -1) {
            networkId = getNetworkId(networkSSID)
        }

        wifiManager.disconnect()
        wifiManager.enableNetwork(networkId, true)
        wifiManager.reconnect()*/
    }


    fun disconnectFromWifi() {
        Timber.d("WiFi disconnectFromWifi")
        disconnecting = true
        wifiResponse.value = WiFiResponse.disconnecting()
        wifiManager.disconnect()
        wifiManager.disableNetwork(networkId)
        wifiManager.removeNetwork(networkId)
        wifiManager.reconnect() // reconnect to previous network
    }

    companion object {
        var disconnecting: Boolean = false
        var networkSSID: String = ""
        var networkPassword: String = ""
        var previousNetworkSSID: String = ""
        var networkId:Int = -1
    }
}