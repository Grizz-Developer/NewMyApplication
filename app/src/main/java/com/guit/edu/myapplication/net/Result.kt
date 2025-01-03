package com.guit.edu.myapplication.net

data class Result<T>(
    val code: Int,
    val message: String,
    val data: T?
)
