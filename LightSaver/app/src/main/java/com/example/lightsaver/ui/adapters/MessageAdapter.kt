package com.example.lightsaver.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lightsaver.R
import com.example.lightsaver.databinding.ActivitySettingsBinding

import com.example.lightsaver.databinding.AdapterLogsBinding
import com.example.lightsaver.util.DateUtils
import com.example.lightsaver.vo.Message

class MessageAdapter(private val items: List<Message>?) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_logs, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null) return 0
        return if (items.isNotEmpty()) items.size else 0
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bindItems(items!![position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: AdapterLogsBinding

        fun bindItems(item: Message) {
            binding = AdapterLogsBinding.bind(itemView)
            binding.textMessage.text = item.message
            binding.textValue.text = item.value
            binding.textDate.text = DateUtils.parseCreatedAtDate(item.createdAt)
        }
    }
}