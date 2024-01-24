package com.bluewhaleyt.plugin_example

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluewhaleyt.plugin_api.Plugin

class ExamplePlugin(context: Context) : Plugin(context) {

    override fun name() = "ExamplePlugin"

    override fun description() = ""

    override fun author() = "BlueWhaleYT"

    override val onCompose: @Composable () -> Unit
        get() = { PluginScreen() }

}

@Composable
internal fun PluginScreen(
    viewModel: ExamplePluginViewModel = viewModel()
) {
    val context = LocalContext.current
    val plugin = ExamplePlugin(context)
    val showDialog = viewModel.showDialog

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = viewModel.count.toString(),
            fontSize = 40.sp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row {
            Button(
                onClick = {
                    viewModel.increment()
                }
            ) {
                Text(text = "Increment")
            }
            Button(
                onClick = {
                    viewModel.showDialog()
                }
            ) {
                Text(text = "Show dialog")
            }
        }
    }
    if (showDialog) {
        PluginInfoDialog(
            plugin = plugin
        )
    }
}

@Composable
internal fun PluginInfoDialog(
    plugin: Plugin,
    viewModel: ExamplePluginViewModel = viewModel()
) {
    AlertDialog(
        onDismissRequest = { viewModel.dismissDialog() },
        confirmButton = {  },
        title = {
            Text(text = "Plugin Info")
        },
        text = {
            Text(text = """
            Plugin Name: ${plugin.name()}
            Plugin Description: ${plugin.description()}
            Plugin Author: ${plugin.author()}
        """.trimIndent())
        }
    )
}