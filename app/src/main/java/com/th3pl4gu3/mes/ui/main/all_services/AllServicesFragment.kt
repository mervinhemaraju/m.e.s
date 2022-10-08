package com.th3pl4gu3.mes.ui.main.all_services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.databinding.FragmentAllServicesBinding
import com.th3pl4gu3.mes.ui.utils.extensions.action
import com.th3pl4gu3.mes.ui.utils.extensions.requireMesActivity
import com.th3pl4gu3.mes.ui.utils.extensions.snackInf
import kotlinx.coroutines.launch

class AllServicesFragment : Fragment() {

    private var mBinding: FragmentAllServicesBinding? = null
    private var mViewModel: AllServicesViewModel? = null

    private val binding get() = mBinding!!
    private val viewModel get() = mViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAllServicesBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(AllServicesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeServices()

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    // Private Functions
    private fun subscribeServices() {
        val servicesAdapter = ServiceAdapter(requireMesActivity())

        binding.RecyclerViewServices.apply {
            /*
            * State that layout size will not change for better performance
            */
            setHasFixedSize(true)

            /* Bind the layout manager */
            layoutManager = LinearLayoutManager(requireContext())

            /* Bind the adapter to the RecyclerView*/
            adapter = servicesAdapter
        }

        viewModel.services.observe(viewLifecycleOwner, { services ->
            if (services != null) {

                lifecycleScope.launch {
                    // Serve services (pun-intended)
                    servicesAdapter.submitList(services)

                    servicesAdapter.notifyDataSetChanged()
                }

                // Updates UI animation
                viewModel.stopLoading()
            }
        })

    }

    private fun subscribeObservers() {
        viewModel.message.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.RootAllServices.snackInf(it) {
                    action(getString(R.string.action_retry)) {
                        viewModel.refreshServices()
                    }
                }
            }
        })
    }
}