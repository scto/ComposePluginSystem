package com.bluewhaleyt.plugin_example

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExamplePluginViewModel : ViewModel() {

    var count by mutableStateOf(0)
        private set

    var showDialog by mutableStateOf(false)
        private set

    fun increment() {
        count++
    }

    fun showDialog() {
        showDialog = true
    }

    fun dismissDialog() {
        showDialog = false
    }

}