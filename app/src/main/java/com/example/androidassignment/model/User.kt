package com.example.androidassignment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
//MODAL class
data class User (
/*//  First Name
    @Json(name = "first")
    val first: String,
//  Last Name
    @Json(name = "last")
    val last: String,*/

    @Json(name = "name")
    var name: Name,

    @Json(name = "email")
    val email: String,

    @Json(name = "picture")
    val picture: Picture,

    //date of birth
    @Json(name = "dob")
    val dob: Birthday
 ):Parcelable

