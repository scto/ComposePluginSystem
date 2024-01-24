package com.bluewhaleyt.composepluginsystem.ui.screen

import android.content.Context
import android.os.Environment
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluewhaleyt.composepluginsystem.Container
import com.bluewhaleyt.plugin_api.PluginManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

var isSuccess by mutableStateOf(false)
var onCompose by mutableStateOf<@Composable () -> Unit>({})

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

    Container { permissionGranted, pluginsDir ->
        if (permissionGranted) {
            val checked = viewModel.checked
            
            Checkbox(
                checked = checked,
                onCheckedChange = { viewModel.onCheckedChange(it) }
            )
            AnimatedContent(targetState = checked, label = "") {
                Column {
                    if (it) {
                        Text(text = "This is the compose layout from ExamplePlugin")
                        LaunchedEffect(key1 = checked) {
                            loadExamplePlugin(context)
                        }
                        if (isSuccess) {
                            onCompose()
                        } else {
                            CircularProgressIndicator()
                        }
                    } else {
                        Text(text = "This is the compose layout from App")
                    }
                }
            }
        }
    }
}

suspend fun loadExamplePlugin(context: Context) {
    withContext(Dispatchers.IO) {
        if (isSuccess) return@withContext

        try {
            val pluginManager = PluginManager()
            val pluginsDir =
                Environment.getExternalStorageDirectory().absolutePath + "/ComposePluginSystem/plugins/"
            val pluginFile =
                pluginManager.loadPluginApkFromFile(context, File("$pluginsDir/ExamplePlugin.apk"))
            val pluginClass = pluginFile?.loadClass("com.bluewhaleyt.plugin_example.ExamplePlugin")

            pluginClass?.let {
                onCompose = pluginManager.invokeOnCompose(
                    context = context,
                    pluginClass = pluginClass
                )
                isSuccess = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}