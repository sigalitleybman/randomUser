package com.example.androidassignment.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//Access this class directly without create instance of it
object ApiClient {

    private val BASE_URl = "https://randomuser.me/api/"

    //****** Moshi is a modern JSON library for Android, Java and Kotlin.
    //It makes it easy to parse JSON into Java and Kotlin classes ******
    //Created variable for moshi builder, adding a converter to it
     private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    //Created an instance of Retrofit by lazy so it can initialized only when it is
    //needed pass the base url and the moshi variables created above
    //-----It's actually build the url to get data from api------
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun getRetrofitFromApiClient(): Retrofit = retrofit

    //It's for allows us to get to the interface and access the users
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}