package com.dicoding.gloo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products/{id}")
    fun getProductById(@Path("id") id: String): Call<ProductResponse>
}
