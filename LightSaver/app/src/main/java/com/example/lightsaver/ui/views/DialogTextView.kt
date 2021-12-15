package com.example.lightsaver.ui.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.lightsaver.R
import com.example.lightsaver.databinding.ActivityLogsBinding.bind

import com.example.lightsaver.databinding.DialogTextViewBinding

class DialogTextView : LinearLayout {

    private var listener: ViewListener? = null
    private var value : String? = null
    private lateinit var binding: DialogTextViewBinding
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = DialogTextViewBinding.bind(this)
        binding.valueText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                if (!TextUtils.isEmpty(charSequence)) {
                    value = charSequence.toString()
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
    }

    fun getValue() : String? {
        return value
    }

    fun setListener(listener: ViewListener) {
        this.listener = listener
    }

    interface ViewListener {
        fun onTextChange(value : String?)
        fun onCancel()
    }
}