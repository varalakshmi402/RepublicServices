package com.varalakshmiakella.republicServices.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.varalakshmiakella.republicServices.db.DriverDatabase
import com.varalakshmiakella.republicServices.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: DriverViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val driverRepository = DriverRepository(DriverDatabase(this))
        val viewModelProviderFactory = DriverViewModelProviderFactory(driverRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[DriverViewModel::class.java]
    }
}