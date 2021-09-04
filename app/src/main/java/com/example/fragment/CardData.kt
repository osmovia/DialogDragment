package com.example.fragment

import java.util.*

data class CardData(val word: String, val translate: String, val id: String = UUID.randomUUID().toString())