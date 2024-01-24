package com.bluewhaleyt.composepluginsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bluewhaleyt.composepluginsystem.ui.theme.ComposePluginSystemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePluginSystemTheme {
                App()
            }
        }
    }
}

