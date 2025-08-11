package com.example.applolc_test.data.api

import com.example.applolc_test.data.model.Customer
import com.example.applolc_test.data.model.TokenRequest
import com.example.applolc_test.data.model.TokenResponse
import com.example.applolc_test.data.model.UpdateCustomerRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/token")
    suspend fun getToken(@Body request: TokenRequest): Response<TokenResponse>

    @GET("api/users")
    suspend fun getCustomers(@Header("Authorization") token: String): Response<List<Customer>>

    @PUT("api/users/{id}")
    suspend fun updateCustomer(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: UpdateCustomerRequest
    ): Response<Map<String, String>>
}