package com.example.lightsaver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import com.example.lightsaver.persistence.MessageDao
import com.example.lightsaver.persistence.Preferences
import javax.inject.Inject

public class MainViewModel @Inject
public constructor(application: Application, private val dataSource: MessageDao,
            private val configuration: Preferences) : AndroidViewModel(application) {

    companion object {

    }
}