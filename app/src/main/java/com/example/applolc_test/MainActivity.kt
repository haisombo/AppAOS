package com.example.applolc_test

import android.app.Application

import com.example.applolc_test.data.local.SharedPrefsManager
import com.example.applolc_test.data.repository.CustomerRepository


class CustomerApplication : Application() {
    lateinit var repository: CustomerRepository

    override fun onCreate() {
        super.onCreate()
        val prefsManager = SharedPrefsManager(this)
        repository = CustomerRepository(prefsManager)
    }
}