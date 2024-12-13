package com.dicoding.gloo.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming Soon!"
    }
    val text: LiveData<String> = _text
}