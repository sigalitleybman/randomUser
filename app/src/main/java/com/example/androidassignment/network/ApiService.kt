package com.example.androidassignment.network

import com.example.androidassignment.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Here we define how Retrofit talks to the service using the Get Method.
 */
interface ApiService {
    /**
     * "." on GET scope - because we should pass the last parameter for our url,
     * and in our case "https://randomuser.me/api/" there is no parameter, thus we put a dot.
     */
    @GET(".")
   // we are "calling" (by Call<UserResponse>) data from our list that we defined in UserResponse class
    fun getUsers(@Query("results") result: Int): Call<UserResponse>
}