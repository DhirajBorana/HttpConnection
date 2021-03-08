package com.example.httpconn.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpconn.Employee
import com.example.httpconn.HttpHelper
import com.example.httpconn.RequestMethod
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.launch
import java.lang.Exception

class EditViewModel : ViewModel() {

    private val mapper by lazy { ObjectMapper().registerKotlinModule() }

    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int>
        get() = _responseCode

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch {
            try {
                val response = mapper.writeValueAsString(employee)
                _responseCode.value = HttpHelper.send(
                    RequestMethod.PUT,
                    "https://dummy.restapiexample.com/api/v1/update/${employee.id}",
                    response
                ).code
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}