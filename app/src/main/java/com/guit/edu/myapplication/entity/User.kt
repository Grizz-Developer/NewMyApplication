package com.guit.edu.myapplication.entity

data class User(
    var id: Int = 0,
    var nickname: String? = null,
    var signature: String? = null,
    var gender: String? = null,
    var username: String? = null,
    var password: String? = null,
    var assignment: Int = 0,
    var height: Int = 0,
    var weight: Int = 0,
    var cupcapacity: Int = 0
)
