package com.bluewhaleyt.plugin_api

import android.content.Context

interface PluginConfig {
    fun name(): String
    fun description(): String
    fun author(): String
}