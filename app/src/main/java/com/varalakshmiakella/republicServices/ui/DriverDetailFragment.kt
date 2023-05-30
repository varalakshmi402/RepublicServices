package com.varalakshmiakella.republicServices.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.varalakshmiakella.republicServices.R
import com.varalakshmiakella.republicServices.databinding.FragmentDetailDriverRouteBinding
import kotlinx.coroutines.runBlocking

class DriverDetailFragment:Fragment(R.layout.fragment_detail_driver_route) {
    lateinit var viewModel :DriverViewModel
    private val args: DriverDetailFragmentArgs by navArgs()
    lateinit var binding : FragmentDetailDriverRouteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailDriverRouteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel
        val driver = args.driver
        runBlocking {
            binding.tvRouteName.append(viewModel.getRouteName(driver.id))
            binding.tvTypeName.append(viewModel.getRouteType(driver.id))
        }
    }
}