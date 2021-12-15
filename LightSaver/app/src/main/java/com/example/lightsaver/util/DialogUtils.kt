package com.example.lightsaver.util

import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper

import com.google.android.material.snackbar.Snackbar

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.example.lightsaver.R
import com.example.lightsaver.ui.views.DialogTextView
import android.content.DialogInterface
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog


class DialogUtils(base: Context) : ContextWrapper(base) {
    
    companion object {

        fun dialogMessage(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogMessage(context: Context, title: String, message: String, listener: DialogInterface.OnClickListener) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, listener)
            builder.create().show();
        }

        fun dialogMessage(context: Context, message: String?) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogMessageHtml(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(fromHtml(message))
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogEditText(context: Context, title:String, value: String, listener: DialogTextView.ViewListener) : AlertDialog {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_text_view, null, false)
            val dialogView = view.findViewById<DialogTextView>(R.id.dialog_text_view)
            dialogView.setListener(listener)
            val valueText = view.findViewById<TextView>(R.id.value_text)
            valueText.text = value;
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(title)
            dialog.setView(view)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onTextChange(dialogView.getValue())
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onCancel()
            }
            dialog.setOnDismissListener({ listener.onCancel() })
            return dialog.show()
        }

        fun dialogPasswordText(context: Context, title:String, value: String, listener: DialogTextView.ViewListener) : AlertDialog {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_password_view, null, false)
            val dialogView = view.findViewById<DialogTextView>(R.id.dialog_text_view)
            dialogView.setListener(listener)
            val valueText = view.findViewById<TextView>(R.id.value_text)
            valueText.text = value;
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(title)
            dialog.setView(view)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onTextChange(dialogView.getValue())
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onCancel()
            }
            dialog.setOnDismissListener({ listener.onCancel() })
            return dialog.show()
        }

        /**
         * Generate a dismissible <code>SnackBar</code> component.
         */
        fun createSnackBar(context: Context, message: String, retry: Boolean, @NonNull view: View, listener: View.OnClickListener): Snackbar {
            return when {
                retry -> {
                    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(context.getString(R.string.button_retry), listener)
                    val textView = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(context.resources.getColor(R.color.white))
                    snackBar
                }
                else -> {
                    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    val textView = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(context.resources.getColor(R.color.white))
                    snackBar
                }
            }
        }
    }
}