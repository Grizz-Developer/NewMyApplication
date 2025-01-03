package com.guit.edu.myapplication.entity

import java.time.LocalDateTime

data class History(
    var id: String? = null,
    var username: String? = null,
    var drink: Int = 0,
    var type: String? = null,
    var createdAt: LocalDateTime? = null
)
