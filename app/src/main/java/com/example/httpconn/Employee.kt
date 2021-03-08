package com.example.httpconn

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Employee(
    val id: String,
    @JsonProperty("employee_name")
    val name: String,
    @JsonProperty("employee_salary")
    val salary: String,
    @JsonProperty("employee_age")
    val age: String,
    @JsonProperty("profile_image")
    val profileImage: String = ""
) : Serializable
