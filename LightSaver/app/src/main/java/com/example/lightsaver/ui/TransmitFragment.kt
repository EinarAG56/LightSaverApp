package com.example.lightsaver.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lightsaver.BaseFragment
import com.example.lightsaver.R
import com.example.lightsaver.api.NetworkResponse
import com.example.lightsaver.api.Status
import com.example.lightsaver.databinding.ContentLightBinding
import com.example.lightsaver.databinding.ContentTransmitBinding
import com.example.lightsaver.util.DialogUtils
import com.example.lightsaver.viewmodel.TransmitViewModel
import javax.inject.Inject

class TransmitFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: TransmitViewModel
    private var listener: OnFragmentInteractionListener? = null
    private var networkStatus: Status = Status.START
    private lateinit var binding: ContentLightBinding
    private var value: String = "red"
    private var isOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentLightBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ContentLightBinding.inflate(layoutInflater)

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(TransmitViewModel::class.java)
        observeViewModel(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switch1.setOnCheckedChangeListener {_,isChecked->
            val Notif: String
            if(isChecked){
                Notif = "on"
            }else{
                Notif = "off"
            }
            Toast.makeText(requireActivity(), "Notification: $Notif", Toast.LENGTH_SHORT).show()
        }
        binding.redButton.setOnClickListener {
            value = "red"
            Toast.makeText(requireActivity(), "Color: $value", Toast.LENGTH_LONG).show()
            if(isOn){
                if(TextUtils.isEmpty(value)) {
                    Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
                } else if (networkStatus != Status.LOADING) {
                    viewModel.sendMessage(value)
                }
            }
        }

        binding.greenButton.setOnClickListener {
            value = "green"
            Toast.makeText(requireActivity(), "Color: $value", Toast.LENGTH_LONG).show()
            if(isOn){
                if(TextUtils.isEmpty(value)) {
                    Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
                } else if (networkStatus != Status.LOADING) {
                    viewModel.sendMessage(value)
                }
            }
        }
        binding.blueButton.setOnClickListener {
            value = "blue"
            Toast.makeText(requireActivity(), "Color: $value", Toast.LENGTH_LONG).show()
            if(isOn){
                if(TextUtils.isEmpty(value)) {
                    Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
                } else if (networkStatus != Status.LOADING) {
                    viewModel.sendMessage(value)
                }
            }
        }
        binding.yellowButton.setOnClickListener {
            value = "yellow"
            Toast.makeText(requireActivity(), "Color: $value", Toast.LENGTH_LONG).show()
            if(isOn){
                if(TextUtils.isEmpty(value)) {
                    Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
                } else if (networkStatus != Status.LOADING) {
                    viewModel.sendMessage(value)
                }
            }
        }
        binding.purpleButton.setOnClickListener {
            value = "purple"
            Toast.makeText(requireActivity(), "Color: $value", Toast.LENGTH_LONG).show()
            if(isOn){
                if(TextUtils.isEmpty(value)) {
                    Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
                } else if (networkStatus != Status.LOADING) {
                    viewModel.sendMessage(value)
                }
            }

        }
        binding.toggleButton.setOnCheckedChangeListener {_,isChecked->
            val color: String = value
            if(isChecked){
                isOn = true

            }else{
                isOn = false
                value = "off"
            }

            if(TextUtils.isEmpty(value)) {
                Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
            } else if (networkStatus != Status.LOADING) {
                viewModel.sendMessage(value)
            }
            value = color
        }
        binding.buttonDisconnect.setOnClickListener {
            listener?.disconnectFromWifi()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun disconnectFromWifi()
        fun wifiDisconnected()
    }

    private fun observeViewModel(viewModel: TransmitViewModel) {
        viewModel.networkResponse().observe(this, Observer {response -> processNetworkResponse(response)})
        viewModel.getToastMessage().observe(this, Observer {message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()})
        viewModel.getAlertMessage().observe(this, Observer {message -> DialogUtils.dialogMessage(requireActivity(), getString(R.string.alert_title_error), message!!, DialogInterface.OnClickListener { _, _ -> SettingsActivity.start(requireActivity()) })
        })
    }

    private fun processNetworkResponse(response: NetworkResponse?) {
        if(response?.status != null) {
            networkStatus = response.status
            when (networkStatus) {
                Status.LOADING -> {
                    //
                }
                Status.SUCCESS -> {
                    //
                }
                Status.ERROR -> {
                    val message = response.error?.message.toString()
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        fun newInstance(): TransmitFragment {
            return TransmitFragment()
        }
    }
    /*@Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: TransmitViewModel
    private var listener: OnFragmentInteractionListener? = null
    private var networkStatus: Status = Status.START
    private lateinit var binding: ContentTransmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentTransmitBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ContentTransmitBinding.inflate(layoutInflater)

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(TransmitViewModel::class.java)
        observeViewModel(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonOn.setOnClickListener {
            val value = binding.editOn.text.toString()
            if(TextUtils.isEmpty(value)) {
                Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
            } else if (networkStatus != Status.LOADING){
                viewModel.sendMessage(value)
            }
        }

        binding.buttonOff.setOnClickListener {
            val value = binding.editOff.text.toString()
            if(TextUtils.isEmpty(value)) {
                Toast.makeText(requireActivity(), getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
            } else if (networkStatus != Status.LOADING) {
                viewModel.sendMessage(value)
            }
        }

        binding.buttonDisconnect.setOnClickListener {
            listener?.disconnectFromWifi()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun disconnectFromWifi()
        fun wifiDisconnected()
    }

    private fun observeViewModel(viewModel: TransmitViewModel) {
        viewModel.networkResponse().observe(this, Observer {response -> processNetworkResponse(response)})
        viewModel.getToastMessage().observe(this, Observer {message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()})
        viewModel.getAlertMessage().observe(this, Observer {message -> DialogUtils.dialogMessage(requireActivity(), getString(R.string.alert_title_error), message!!, DialogInterface.OnClickListener { _, _ -> SettingsActivity.start(requireActivity()) })
        })
    }

    private fun processNetworkResponse(response: NetworkResponse?) {
        if(response?.status != null) {
            networkStatus = response.status
            when (networkStatus) {
                Status.LOADING -> {
                    //
                }
                Status.SUCCESS -> {
                    //
                }
                Status.ERROR -> {
                    val message = response.error?.message.toString()
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        fun newInstance(): TransmitFragment {
            return TransmitFragment()
        }
    }*/
}// Required empty public constructor
