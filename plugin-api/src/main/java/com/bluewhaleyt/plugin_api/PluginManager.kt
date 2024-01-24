package com.bluewhaleyt.plugin_api

import android.content.Context
import android.os.Environment
import androidx.compose.runtime.Composable
import dalvik.system.DexClassLoader
import java.io.File

class PluginManager {

    var pluginClassLoader: DexClassLoader? = null

    fun loadPluginApkFromFile(
        context: Context,
        file: File,
    ): DexClassLoader? {
        val filesDir = context.externalCacheDir
        val apkFile = File(filesDir?.absolutePath, file.name)
        file.copyTo(apkFile, true)

        val dexFile = File(filesDir, "dex")
        if (!dexFile.exists()) {
            dexFile.mkdirs()
        }

        pluginClassLoader = DexClassLoader(
            apkFile.absolutePath,
            apkFile.absolutePath,
            null,
            context.classLoader
        )
        return pluginClassLoader
    }

    fun invokeOnCompose(
        context: Context,
        pluginClass: Class<*>
    ): @Composable () -> Unit {
        val method = pluginClass
            .getDeclaredMethod("getOnCompose")

        method.isAccessible = true

        val obj = pluginClass
            .getDeclaredConstructor(Context::class.java)
            .newInstance(context)

        @Suppress("UNCHECKED_CAST")
        return method.invoke(obj) as (@Composable () -> Unit)
    }

}
