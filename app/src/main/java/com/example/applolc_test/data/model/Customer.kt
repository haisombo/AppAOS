package com.example.applolc_test.data.model

import java.io.Serializable

data class Customer (

    val id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val company: String,
    val email: String,
    val website: String

) : Serializable