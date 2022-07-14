package com.example.androidassignment.utils

import com.example.androidassignment.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

abstract class URLBuilder {
    private val BASE_URl = "https://randomuser.me/api/"

    /**
     * Moshi is a modern JSON library for Android, Java and Kotlin.
     * It makes it easy to parse JSON into Java and Kotlin classes.
     * Created variable for moshi builder, adding a converter to it.
     */
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    abstract fun URLbuild()

    fun getBaseUrl(): String{
        return BASE_URl
    }

    fun getMoshi(): Moshi{
        return moshi
    }

}