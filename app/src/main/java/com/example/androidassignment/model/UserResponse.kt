package com.example.androidassignment.model

import com.squareup.moshi.Json

//Give as the JSON results
data class UserResponse(
    //List of users that we will get
    @Json(name = "results")
    val result: List<UserInfo>
)