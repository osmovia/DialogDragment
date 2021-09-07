package com.example.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassViewModel : ViewModel() {

    val wordsList: MutableLiveData<MutableList<CardData>> by lazy {
        MutableLiveData<MutableList<CardData>>()
    }

    val data: MutableLiveData<CardData> by lazy {
        MutableLiveData<CardData>()
    }
    val finished: MutableLiveData<CardData> by lazy {
        MutableLiveData<CardData>()
    }

}
