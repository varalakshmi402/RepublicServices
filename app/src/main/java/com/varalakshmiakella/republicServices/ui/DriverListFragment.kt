package com.varalakshmiakella.republicServices.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.varalakshmiakella.republicServices.R
import com.varalakshmiakella.republicServices.adapter.DriverAdapter
import com.varalakshmiakella.republicServices.databinding.FragmentDriverListBinding
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.util.Constants
import com.varalakshmiakella.republicServices.util.Resource
import com.google.android.material.snackbar.Snackbar

private const val TAG="DRIVER LIST FRAGMENT"
class DriverListFragment :Fragment(R.layout.fragment_driver_list){
    private lateinit var viewModel : DriverViewModel
    private var driverAdapter = DriverAdapter()
    private lateinit var binding : FragmentDriverListBinding
    private var driverList : List<Driver>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriverListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()
        driverAdapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("driver",it)
            }
            findNavController().navigate(
                R.id.action_driverListFragment_to_driverDetailFragment,
                bundle
            )
        }
        binding.sort.setOnClickListener {
                driverAdapter.differ.submitList(driverList?.let { sortDriverList(it) })
                Snackbar.make(view,Constants.SORT_MESSAGE, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.driverRoute.observe(viewLifecycleOwner, Observer {
            response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let{
                        driverResponse->
                        driverList = driverResponse.drivers.toList()
                        driverAdapter.differ.submitList(driverList)

                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{
                        message->
                        Log.e(TAG,"An Error occurred :$message")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }


    private fun setUpRecyclerView(){
        driverAdapter = DriverAdapter()
        binding.rvDriverList.apply{
            adapter = driverAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
    }

    private fun sortDriverList(driverList : List<Driver>):List<Driver>{
        return driverList.sortedBy { it.toString().split(" ").last() }
    }
}