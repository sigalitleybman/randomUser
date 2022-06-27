package com.example.androidassignment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture (
    @Json(name = "large")
    val large: String
): Parcelable