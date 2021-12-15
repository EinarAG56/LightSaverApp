package com.example.lightsaver.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.Spanned
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun fromHtml(html: String?): Spanned {
    val result: Spanned
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        result = Html.fromHtml(html)
    }
    return result
}

fun broadcastReceiver(init: (Context, Intent?) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        public override fun onReceive(context: Context, intent: Intent?) {
            init(context, intent)
        }
    }
}