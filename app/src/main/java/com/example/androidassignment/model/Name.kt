package com.example.androidassignment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Name (
    //  First Name
    @Json(name = "first")
    val first: String,

    //  Last Name
    @Json(name = "last")
    val last: String
):Parcelable