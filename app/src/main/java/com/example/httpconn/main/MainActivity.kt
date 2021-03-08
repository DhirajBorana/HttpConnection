package com.example.httpconn.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.httpconn.*
import com.example.httpconn.databinding.ActivityMainBinding
import com.example.httpconn.employee.EmployeeActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var adapter: EmployeeAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapter()
        viewModel.getAll()
        viewModel.employees.observe(this) {
            adapter.submitList(it)
        }

        viewModel.responseCode.observe(this) {
            when(it) {
                200 -> Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                404 -> Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
                429 -> Toast.makeText(this, "Too many request", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        adapter = EmployeeAdapter(object : EmployeeAdapter.Callback {
            override fun onClicked(position: Int) {
                val intent = Intent(this@MainActivity, EmployeeActivity::class.java)
                intent.putExtra("EMPLOYEE", adapter.currentList[position])
                startActivity(intent)
            }
        })
        binding.employeeRv.adapter = adapter
    }
}