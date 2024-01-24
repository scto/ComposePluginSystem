package com.bluewhaleyt.plugin_api

import androidx.compose.runtime.Composable

interface PluginBase : PluginConfig {
    val onCompose: (@Composable () -> Unit)
}