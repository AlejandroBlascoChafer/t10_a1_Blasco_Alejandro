package com.example.api.model

import android.service.autofill.UserData

data class UserIdResponse(
    val data: UserData
)
data class UserData(
    val User: User
)

data class User(
    val id: Int
)
