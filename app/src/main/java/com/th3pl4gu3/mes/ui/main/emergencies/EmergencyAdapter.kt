package com.th3pl4gu3.mes.ui.main.emergencies

import com.th3pl4gu3.mes.api.Service
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener

class EmergencyAdapter(
    private val phoneNumberListener: PhoneNumberListener
) : ListAdapter<Service, EmergencyViewHolder>(
    diffCallback
) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        holder.bind(
            phoneNumberListener,
            getItem(position)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        return EmergencyViewHolder.from(
            parent
        )
    }
}