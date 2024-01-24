package com.bluewhaleyt.composepluginsystem.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var checked by mutableStateOf(false)
        private set

    fun onCheckedChange(newChecked: Boolean) {
        checked = newChecked
    }

}