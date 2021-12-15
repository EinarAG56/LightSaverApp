package com.example.lightsaver.ui

import androidx.lifecycle.ViewModelProvider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lightsaver.BaseFragment
import com.example.lightsaver.R
import com.example.lightsaver.databinding.ContentMainBinding
import com.example.lightsaver.viewmodel.MainViewModel
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainViewModel
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var binding: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ContentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        observeViewModel(viewModel)
    }

    /*Snackbar.make(activity!!.findViewById(android.R.id.content), "Replace with your own action", Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show()*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonConnect.setOnClickListener {
            Timber.d("WiFi Connect")
            listener?.connectWifi()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun observeViewModel(viewModel: MainViewModel) {

    }

    interface OnFragmentInteractionListener {
        fun wifiConnected()
        fun connectWifi()
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}// Required empty public constructor
