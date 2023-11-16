package com.loschimbitas.parchados.activities.globales

import com.loschimbitas.parchados.model.User

class Globales {

    // Variable global necesaria para el programa
    // Tal vez innecesaria en el futuro dada la base de datos
    companion object {
        var userGlobal = User(0, "fake","fake","fake",
            "fake", "fake", false, "fake", "fake", "fake" )
    }
}