package com.felix.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.felix.datastore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var pref: CounterDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = CounterDataStoreManager(this)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        setupView()
        setObserver()
    }

    private fun setupView() {
        binding.apply {
            btnPlus.setOnClickListener {
                incrementCount()
            }

            btnMin.setOnClickListener {
                decrementCount()
            }

            btnSetValue.setOnClickListener {
                viewModel.saveDataStore(binding.tvAngka.text.toString().toInt())
            }
        }
    }

    private fun decrementCount() {
        viewModel.decrementCounter()
    }

    private fun incrementCount() {
        viewModel.incrementCount()
    }

    private fun setObserver(){
        viewModel.apply {
            getDataStore().observe(this@MainActivity){
                binding.tvAngka.text = it.toString()
            }

            vCounter.observe(this@MainActivity){result ->
                binding.tvAngka.text = result.toString()
            }
        }
    }
}