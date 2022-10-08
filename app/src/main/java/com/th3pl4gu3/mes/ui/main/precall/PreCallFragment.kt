package com.th3pl4gu3.mes.ui.main.precall

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.FragmentPrecallBinding
import com.th3pl4gu3.mes.ui.utils.extensions.*
import java.util.concurrent.TimeUnit


class PreCallFragment : Fragment() {

    private var mBinding: FragmentPrecallBinding? = null
    private var mViewModel: PreCallViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    private val mCountDown = object : CountDownTimer(5000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            viewModel.tick(TimeUnit.SECONDS.convert(millisUntilFinished, TimeUnit.MILLISECONDS))
        }

        override fun onFinish() {
            startActivity(viewModel.retrievedService?.number.toString().toPhoneCallIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPrecallBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PreCallViewModel::class.java)
        binding.viewModel = viewModel
        // Bind lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the Main Toolbar
        requireMesToolBar().hide()

        // Subscribe Listeners
        subscribeListeners()

        // Update service data received and subscribe the countdown
        updateServiceDataAndStartCountdown()
    }

    override fun onPause() {
        super.onPause()

        /*
        * If the countdown is successful or
        * if the user leaves the screen prematurely
        * fragment will be popped out of back stack
        * and countdown will be cancelled
        */

        // Cancel countdown
        mCountDown.cancel()

        if (findNavController().currentDestination?.id == R.id.Fragment_PreCall) {
            // Go back to previous page
            pop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show the Main Toolbar
        requireMesToolBar().show()

        mBinding = null
    }

    private fun subscribeListeners() {

        // Cancel button listener
        binding.ButtonCancel.setOnClickListener {
            pop()
        }
    }

    private fun updateServiceDataAndStartCountdown() {

        // Get the service passed
        val serviceRetrieved = PreCallFragmentArgs.fromBundle(requireArguments()).argsservice

        // Update fields with service data
        viewModel.updateService(serviceRetrieved)

        // Start countdown
        mCountDown.start()
    }
}