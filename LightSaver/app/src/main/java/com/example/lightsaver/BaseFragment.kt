package com.example.lightsaver

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.lightsaver.persistence.Preferences
import com.example.lightsaver.ui.ConnectionLiveData
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @Inject lateinit var preferences: Preferences
    val disposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
    }
}// Required empty public constructor
