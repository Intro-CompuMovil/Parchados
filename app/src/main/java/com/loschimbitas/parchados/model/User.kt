package com.loschimbitas.parchados.model

data class User(
    val id: Long,
    var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var isProfessor: Boolean,
    var about: String,
    var imageUrl: String,
    var age: String
)
