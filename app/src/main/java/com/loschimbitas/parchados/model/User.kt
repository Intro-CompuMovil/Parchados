package com.loschimbitas.parchados.model

data class User(
    val id: Long,
    var username: String,
    val firstName: String,
    val lastName: String,
    var email: String,
    var password: String,
    var isProfessor: Boolean,
)
