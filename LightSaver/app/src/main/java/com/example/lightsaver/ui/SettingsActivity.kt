package com.example.lightsaver.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.lightsaver.BaseActivity
import com.example.lightsaver.R
import com.example.lightsaver.databinding.ActivitySettingsBinding
import com.example.lightsaver.ui.views.DialogTextView
import com.example.lightsaver.util.DialogUtils
import com.example.lightsaver.util.IntentUtils
import timber.log.Timber

class SettingsActivity : BaseActivity() {

    private var dialog: AlertDialog? = null
    private lateinit var binding: ActivitySettingsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_settings)

        if(TextUtils.isEmpty(preferences.address())) {
            Timber.d(preferences.address())
            binding.apiText.text = getString(R.string.pref_address_description)
        } else {
            binding.apiText.text = preferences.address()
        }
        binding.apiButton.setOnClickListener {
            val address : String? = preferences.address()
            dialog = DialogUtils.dialogEditText(this@SettingsActivity, getString(R.string.pref_address), address!!,
                object : DialogTextView.ViewListener {
                    override fun onTextChange(value: String?) {
                        if(!TextUtils.isEmpty(value)) {
                            preferences.address(value!!)
                            binding.apiText.text = preferences.address()
                        }
                    }
                    override fun onCancel() {
                        dialog?.dismiss()
                    }
                })
        }
        binding.passwordButton.setOnClickListener {
            val password : String? = preferences.password()
            dialog = DialogUtils.dialogPasswordText(this@SettingsActivity, getString(R.string.pref_password), password!!,
                object : DialogTextView.ViewListener {
                    override fun onTextChange(value: String?) {
                        if(!TextUtils.isEmpty(value)) {
                            preferences.password(value!!)
                            binding.passwordText.text = preferences.password()
                        }
                    }
                    override fun onCancel() {
                        dialog?.dismiss()
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_settings
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(newIntent(activity))
        }
        private fun newIntent(context: Context): Intent {
            return IntentUtils.newIntent(context, SettingsActivity::class.java)
        }
    }
}