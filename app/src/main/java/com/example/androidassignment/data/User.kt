package com.example.androidassignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//This class created for create table of User in the DB by @Entity annotation
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val picture: String,
    val age: Int
)
