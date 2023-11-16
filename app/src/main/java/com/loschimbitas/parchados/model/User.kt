package com.loschimbitas.parchados.model

data class User(
    val id: Long? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var isProfessor: Boolean,
    var about: String? = null,
    var imageUrl: String? = null,
    var age: String? = null
)
