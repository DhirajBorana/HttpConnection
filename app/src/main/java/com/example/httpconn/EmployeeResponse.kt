package com.example.httpconn

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class EmployeeResponse(
    @JsonProperty("status")
    val status: String,
    @JsonProperty("data")
    val data: List<Employee>,
    @JsonIgnore
    val message: String = ""
)
