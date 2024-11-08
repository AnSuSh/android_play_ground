package com.example.androidplayground

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var uri: Uri? by mutableStateOf(null)
        private set

    fun updateUri(uri: Uri?) {
        Log.d("Main","setting the uri - $uri")
        this.uri = uri
    }
}