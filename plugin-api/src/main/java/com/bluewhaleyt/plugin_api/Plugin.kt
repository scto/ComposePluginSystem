package com.bluewhaleyt.plugin_api

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable

open class Plugin(
    private val context: Context,
) : PluginBase {

    override fun name(): String {
        TODO("Not yet implemented")
    }

    override fun description(): String {
        TODO("Not yet implemented")
    }

    override fun author(): String {
        TODO("Not yet implemented")
    }

    override val onCompose: @Composable () -> Unit
        get() = {}

}