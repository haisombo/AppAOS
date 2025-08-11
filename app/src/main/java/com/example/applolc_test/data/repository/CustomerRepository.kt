package com.example.applolc_test.data.repository

import android.util.Log
import com.example.applolc_test.data.api.RetrofitClient
import com.example.applolc_test.data.local.SharedPrefsManager
import com.example.applolc_test.data.model.Customer
import com.example.applolc_test.data.model.TokenRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerRepository(private val prefsManager: SharedPrefsManager) {
    private val apiService = RetrofitClient.apiService
    private val TAG = "CustomerRepository"
    private val gson = Gson()

    suspend fun authenticate(): Result<String> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "=== AUTHENTICATION START ===")

            val request = TokenRequest("test_client", "super_secret")
            Log.d(TAG, "Token Request: ${gson.toJson(request)}")

            val response = apiService.getToken(request)

            Log.d(TAG, "Response Code: ${response.code()}")
            Log.d(TAG, "Response Message: ${response.message()}")
            Log.d(TAG, "Response Headers: ${response.headers()}")

            if (response.isSuccessful) {
                response.body()?.let { tokenResponse ->
                    Log.d(TAG, "=== TOKEN RESPONSE SUCCESS ===")
                    Log.d(TAG, "Access Token: ${tokenResponse.accessToken}")
                    Log.d(TAG, "Expiration: ${tokenResponse.expiration}")
                    Log.d(TAG, "Full Response: ${gson.toJson(tokenResponse)}")

                    val token = "Bearer ${tokenResponse.accessToken}"
                    prefsManager.saveToken(token, tokenResponse.expiration)

                    Log.d(TAG, "Token saved to SharedPreferences")
                    Log.d(TAG, "=== AUTHENTICATION END ===")

                    Result.success(token)
                } ?: run {
                    Log.e(TAG, "=== AUTHENTICATION FAILED ===")
                    Log.e(TAG, "Response body is null")
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Log.e(TAG, "=== AUTHENTICATION FAILED ===")
                Log.e(TAG, "Error Code: ${response.code()}")
                Log.e(TAG, "Error Body: ${response.errorBody()?.string()}")
                Result.failure(Exception("Authentication failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "=== AUTHENTICATION EXCEPTION ===")
            Log.e(TAG, "Exception Type: ${e.javaClass.simpleName}")
            Log.e(TAG, "Exception Message: ${e.message}")
            Log.e(TAG, "Stack Trace:", e)
            Result.failure(e)
        }
    }

    suspend fun getCustomers(): Result<List<Customer>> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "=== GET CUSTOMERS START ===")

            var token = prefsManager.getToken()
            Log.d(TAG, "Retrieved token from prefs: $token")

            if (token == null) {
                Log.d(TAG, "No token found, authenticating...")
                val authResult = authenticate()
                if (authResult.isFailure) {
                    Log.e(TAG, "Authentication failed during getCustomers")
                    return@withContext Result.failure(authResult.exceptionOrNull()!!)
                }
                token = authResult.getOrNull()
                Log.d(TAG, "New token obtained: $token")
            }

            Log.d(TAG, "Making API call to get customers...")
            Log.d(TAG, "Using token: $token")

            val response = apiService.getCustomers(token!!)

            Log.d(TAG, "Response Code: ${response.code()}")
            Log.d(TAG, "Response Message: ${response.message()}")

            if (response.isSuccessful) {
                val customers = response.body() ?: emptyList()

                Log.d(TAG, "=== CUSTOMERS RESPONSE SUCCESS ===")
                Log.d(TAG, "Number of customers: ${customers.size}")

                customers.forEachIndexed { index, customer ->
                    Log.d(TAG, "Customer #${index + 1}:")
                    Log.d(TAG, "  ID: ${customer.id}")
                    Log.d(TAG, "  Name: ${customer.name}")
                    Log.d(TAG, "  Phone: ${customer.phone}")
                    Log.d(TAG, "  Email: ${customer.email}")
                    Log.d(TAG, "  Address: ${customer.address}")
                    Log.d(TAG, "  Company: ${customer.company}")
                    Log.d(TAG, "  Website: ${customer.website}")
                    Log.d(TAG, "  JSON: ${gson.toJson(customer)}")
                }

                Log.d(TAG, "Full Response JSON: ${gson.toJson(customers)}")
                Log.d(TAG, "=== GET CUSTOMERS END ===")

                Result.success(customers)
            } else if (response.code() == 401) {
                Log.w(TAG, "Token expired (401), re-authenticating...")

                // Token expired, re-authenticate
                val authResult = authenticate()
                if (authResult.isSuccess) {
                    val newToken = authResult.getOrNull()!!
                    Log.d(TAG, "Re-authentication successful, retrying with new token")

                    val retryResponse = apiService.getCustomers(newToken)
                    Log.d(TAG, "Retry Response Code: ${retryResponse.code()}")

                    if (retryResponse.isSuccessful) {
                        val customers = retryResponse.body() ?: emptyList()
                        Log.d(TAG, "Retry successful, got ${customers.size} customers")
                        Log.d(TAG, "Customers: ${gson.toJson(customers)}")
                        Result.success(customers)
                    } else {
                        Log.e(TAG, "Retry failed: ${retryResponse.code()}")
                        Log.e(TAG, "Error Body: ${retryResponse.errorBody()?.string()}")
                        Result.failure(Exception("Failed to fetch customers: ${retryResponse.code()}"))
                    }
                } else {
                    Log.e(TAG, "Re-authentication failed")
                    Result.failure(authResult.exceptionOrNull()!!)
                }
            } else {
                Log.e(TAG, "=== GET CUSTOMERS FAILED ===")
                Log.e(TAG, "Error Code: ${response.code()}")
                Log.e(TAG, "Error Body: ${response.errorBody()?.string()}")
                Result.failure(Exception("Failed to fetch customers: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "=== GET CUSTOMERS EXCEPTION ===")
            Log.e(TAG, "Exception Type: ${e.javaClass.simpleName}")
            Log.e(TAG, "Exception Message: ${e.message}")
            Log.e(TAG, "Stack Trace:", e)
            Result.failure(e)
        }
    }

    fun isCustomerVisited(customerId: Int): Boolean {
        val visited = prefsManager.isCustomerVisited(customerId)
        Log.d(TAG, "Customer $customerId visited status: $visited")
        return visited
    }

    fun markCustomerAsVisited(customerId: Int) {
        Log.d(TAG, "Marking customer $customerId as visited")
        prefsManager.markCustomerAsVisited(customerId)
        Log.d(TAG, "Customer $customerId marked as visited successfully")
    }
}
