package com.th3pl4gu3.mes.ui.main.all_services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.databinding.CustomRecyclerviewServiceBinding
import com.th3pl4gu3.mes.ui.utils.listeners.PhoneNumberListener

class ServiceViewHolder private constructor(val binding: CustomRecyclerviewServiceBinding) :
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
        fun from(parent: ViewGroup): ServiceViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                CustomRecyclerviewServiceBinding.inflate(layoutInflater, parent, false)
            return ServiceViewHolder(
                binding
            )
        }
    }
}

