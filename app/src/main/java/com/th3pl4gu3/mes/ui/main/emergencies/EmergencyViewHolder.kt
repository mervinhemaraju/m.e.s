package com.th3pl4gu3.mes.ui.main.emergencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.databinding.CustomRecyclerviewEmergenciesBinding
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener

class EmergencyViewHolder private constructor(val binding: CustomRecyclerviewEmergenciesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        phoneNumberListener: PhoneNumberListener,
        service: Service?
    ) {
        binding.service = service
        binding.phoneNumberListener = phoneNumberListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): EmergencyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                CustomRecyclerviewEmergenciesBinding.inflate(layoutInflater, parent, false)
            return EmergencyViewHolder(
                binding
            )
        }
    }
}

