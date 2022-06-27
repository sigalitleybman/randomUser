package com.example.androidassignment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Birthday (
    @Json(name = "date")
    val date: String,

    @Json(name = "age")
    val age: Int
): Parcelable