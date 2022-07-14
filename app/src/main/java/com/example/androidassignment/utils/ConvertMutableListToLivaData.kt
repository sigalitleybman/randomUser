package com.example.androidassignment.utils

import androidx.lifecycle.LiveData

class ConvertMutableListToLivaData {
    companion object{
        fun <T>convertMutableListToLivaData(mutableList: MutableList<T>): LiveData<List<T>> {
             return mutableList as LiveData<List<T>>
        }
    }
}