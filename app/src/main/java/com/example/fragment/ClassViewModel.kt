package com.example.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassViewModel : ViewModel() {
     val dataAddNewWord: MutableLiveData<CardData> by lazy {
        MutableLiveData<CardData>()
    }
    val dataChangeNewWord: MutableLiveData<CardData> by lazy {
        MutableLiveData<CardData>()
    }
    val dataDeleteWord: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}