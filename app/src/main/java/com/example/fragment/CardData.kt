package com.example.fragment

import java.io.Serializable
import java.util.*

data class CardData(
    var word: String,
    var translate: String,
    val id: String = UUID.randomUUID().toString()
) : Serializable
