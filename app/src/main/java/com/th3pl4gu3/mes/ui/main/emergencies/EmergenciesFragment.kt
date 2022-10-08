package com.th3pl4gu3.mes.ui.main.emergencies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.FragmentEmergenciesBinding
import com.th3pl4gu3.mes.ui.utils.extensions.action
import com.th3pl4gu3.mes.ui.utils.extensions.requireMesActivity
import com.th3pl4gu3.mes.ui.utils.extensions.snack
import com.th3pl4gu3.mes.ui.utils.extensions.snackInf

class EmergenciesFragment : Fragment() {

    private var mBinding: FragmentEmergenciesBinding? = null
    private var mViewModel: EmergenciesViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentEmergenciesBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(EmergenciesViewModel::class.java)
        binding.viewModel = viewModel
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeButtons()

        subscribeEmergencies()

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun subscribeButtons() {
        // Emergency Button
        binding.EmergencyButton.setOnLongClickListener {

            // Gets the main emergency service
            val service = viewModel.emergencyButtonHolder

            // Call the central function for handling
            // phone intents
            requireMesActivity().onPhoneNumberClicked(service!!)

            true
        }

        binding.EmergencyButton.setOnClickListener {
            viewModel.emergencyButtonClick()
        }
    }

    private fun subscribeEmergencies() {
        val emergencyAdapter = EmergencyAdapter(requireMesActivity())

        binding.RecyclerViewEmergencies.apply {
            /*
            * State that layout size will not change for better performance
            */
            setHasFixedSize(true)

            /* Bind the layout manager */
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

            /* Bind the adapter to the RecyclerView*/
            adapter = emergencyAdapter
        }

        viewModel.emergencies.observe(viewLifecycleOwner, { emergencies ->
            if (emergencies != null) {

                // Serve emergencies
                emergencyAdapter.submitList(emergencies)

                // Updates UI animation
                viewModel.stopLoading()
            }
        })
    }

    private fun subscribeObservers() {
        // Observes error messages
        viewModel.message.observe(viewLifecycleOwner, { error ->
            if (error != null) {
                binding.RootEmergencies.snackInf(error) {
                    action(getString(R.string.action_retry)) {
                        viewModel.refreshServices()
                    }
                }
            }
        })

        // Observes unsuccessful clicks
        viewModel.unsuccessfulClicks.observe(viewLifecycleOwner, { clicks ->
            if (clicks > 2) {
                binding.RootEmergencies.snack(getString(R.string.message_info_hold_emergency_button)) {}

                viewModel.resetEmergencyButtonClicks()
            }
        })
    }
}