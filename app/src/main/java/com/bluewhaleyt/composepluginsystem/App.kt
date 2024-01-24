package com.bluewhaleyt.composepluginsystem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.bluewhaleyt.composepluginsystem.ui.screen.MainScreen
import java.io.File

@Composable
fun App() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun requestAllFileAccess(
    context: Context
) {
    if (!Environment.isExternalStorageManager()) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.setData(uri)
        context.startActivity(intent)
    }
}

@Composable
fun Container(
    content: @Composable ColumnScope.(
        permissionGranted: Boolean, pluginsDir: File
    ) -> Unit
) {
    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val readWritePermissionGranted = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    val permissionsGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Environment.isExternalStorageManager() && readWritePermissionGranted
    } else readWritePermissionGranted


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {

        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(permissions)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requestAllFileAccess(context)
        val pluginsDir = File(Environment.getExternalStorageDirectory().absolutePath + "/ComposePluginSystem/plugins/")
        if (permissionsGranted) {
            pluginsDir.takeIf { !it.exists()
                it.mkdirs()
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content(permissionsGranted, pluginsDir)
        }
    }
}
