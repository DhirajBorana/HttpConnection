package com.example.httpconn.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpconn.Employee
import com.example.httpconn.IHttpHelper
import com.example.httpconn.RequestMethod
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.launch

class EditViewModel(private val httpHelper: IHttpHelper) : ViewModel() {

    private val mapper by lazy { ObjectMapper().registerKotlinModule() }

    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int>
        get() = _responseCode

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch {
            val response = mapper.writeValueAsString(employee)
            _responseCode.value = httpHelper.send(
                RequestMethod.PUT,
                "https://dummy.restapiexample.com/api/v1/update/${employee.id}",
                response
            ).code
        }
    }
}