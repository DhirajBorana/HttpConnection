package com.example.httpconn.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpconn.Employee
import com.example.httpconn.EmployeeResponse
import com.example.httpconn.HttpHelper
import com.example.httpconn.RequestMethod
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val mapper by lazy { ObjectMapper().registerKotlinModule() }

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>>
        get() = _employees

    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int>
        get() = _responseCode

    fun getAll() {
        viewModelScope.launch {
            try {
                val response = HttpHelper.send(RequestMethod.GET,"https://dummy.restapiexample.com/api/v1/employees")
                _employees.value = mapper.readValue(response.body, EmployeeResponse::class.java).data
                _responseCode.value = response.code
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}