package com.zzh133.country.ui.screens.settings

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zzh133.country.R
import com.zzh133.country.data.SettingsDataStore
import com.zzh133.country.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.util.Locale


fun updateLocale(context: Context, language: String) {
    val locale = when (language) {
        "中文" -> Locale.SIMPLIFIED_CHINESE
        else -> Locale.ENGLISH
    }
    Locale.setDefault(locale)

    val config = Configuration()
    config.setLocale(locale)

    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

fun labelToDataSourceCode(label: String): String {
    return when (label) {
        "接口数据", "API" -> "API"
        "本地文件", "Local File" -> "Local File"
        else -> label // 默认直接返回
    }
}

// 简单暴力地重启 Activity
//fun languageCodeToLabel(code: String): String {
//    return when (code) {
//        "zh" -> "中文"
//        else -> "English"
//    }
//}

//fun labelToLanguageCode(label: String): String {
//    return when (label) {
//        "中文" -> "zh"
//        else -> "en"
//    }
//}

fun restartApp(context: Context) {
    val activity = context as? Activity ?: return
    val intent = activity.intent
    activity.finish()
    context.startActivity(intent)
}



@Composable
fun SettingsScreen(
    navController: NavController,
    onThemeChange: (String) -> Unit,
    onTemperatureUnitChange: (String) -> Unit,
    onDataSourceChange: (String) -> Unit,
    onFileSelected: (Uri?) -> Unit,
    onLanguageChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val dataSource = SettingsDataStore.getDataSource(context)
    val language = SettingsDataStore.getLanguage(context)
    val theme = SettingsDataStore.getTheme(context)
    val temperatureUnit = SettingsDataStore.getTemperatureUnit(context)

    var selectedDataSource by remember { mutableStateOf(dataSource) }
    var selectedLanguage by remember { mutableStateOf(language) }
    var selectedTheme by remember { mutableStateOf(theme) }
    var selectedTemperatureUnit by remember { mutableStateOf(temperatureUnit) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    var showTempUnitDialog by remember { mutableStateOf(false) }
    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            selectedFileUri = uri
            onFileSelected(uri)
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.settings_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        // 🌐 Language
        Text(stringResource(id = R.string.language_title))
        DropdownMenuBox(selectedLanguage, listOf("English", "中文")) { selected ->
            selectedLanguage = selected
            onLanguageChange(selected)
            coroutineScope.launch {
                SettingsDataStore.saveLanguage(context, selected)
                updateLocale(context, selected)
                restartApp(context)
            }
        }

//        Text(
//            text = stringResource(id = R.string.settings_title),
//            style = MaterialTheme.typography.headlineMedium
//        )
//        Spacer(Modifier.height(16.dp))
//
//// 🌐 Language
//        Text(stringResource(id = R.string.language_title))
//
//        val languageOptions = listOf(
//            stringResource(id = R.string.language_english),
//            stringResource(id = R.string.language_chinese)
//        )
//
//        DropdownMenuBox(
//            selectedItem = languageCodeToLabel(selectedLanguage),
//            itemList = languageOptions
//        ) { selectedLabel ->
//            val selectedCode = labelToLanguageCode(selectedLabel)
//            selectedLanguage = selectedCode
//            coroutineScope.launch {
//                SettingsDataStore.saveLanguage(context, selectedCode)
//                updateLocale(context, selectedCode)
//                restartApp(context)
//            }
//        }

        Spacer(Modifier.height(24.dp))

        // Theme
//        Text(stringResource(id = R.string.theme_title))
//        DropdownMenuBox(selectedTheme, listOf("Auto", "Light", "Dark")) { selected ->
//            selectedTheme = selected
//            onThemeChange(selected)
//            coroutineScope.launch {
//                SettingsDataStore.saveTheme(context, selected)
//            }
//        }

        Text(stringResource(id = R.string.theme_title))

        val themeOptions = listOf(
            stringResource(id = R.string.theme_auto),
            stringResource(id = R.string.theme_light),
            stringResource(id = R.string.theme_dark)
        )

        DropdownMenuBox(
            selectedItem = selectedTheme,
            itemList = themeOptions
        ) { selectedLabel ->
            selectedTheme = selectedLabel
            onThemeChange(selectedLabel)
            coroutineScope.launch {
                SettingsDataStore.saveTheme(context, selectedLabel)
            }
        }

        Spacer(Modifier.height(24.dp))

        // 🌡 Temperature Unit
        Text(stringResource(id = R.string.temperature_unit_title))
        Spacer(Modifier.height(8.dp))
        Button(onClick = { showTempUnitDialog = true }) {
            Text(selectedTemperatureUnit)
        }

        if (showTempUnitDialog) {
            AlertDialog(
                onDismissRequest = { showTempUnitDialog = false },
                title = { Text(stringResource(id = R.string.choose_temp_unit)) },
                text = {
                    Column {
                        listOf("°C", "°F").forEach { unitOption ->
                            TextButton(
                                onClick = {
                                    selectedTemperatureUnit = unitOption
                                    onTemperatureUnitChange(unitOption)
                                    coroutineScope.launch {
                                        SettingsDataStore.saveTemperatureUnit(context, unitOption)
                                    }
                                    showTempUnitDialog = false
                                }
                            ) {
                                Text(unitOption)
                            }
                        }
                    }
                },
                confirmButton = { Spacer(modifier = Modifier.height(0.dp)) } // 留一个空的confirmButton
            )
        }

        Spacer(Modifier.height(24.dp))

        // Data Source
        Text(stringResource(id = R.string.data_source_title))
//        val dataSourceOptions = listOf("API", "Local File")

        val dataSourceOptions = listOf(
            stringResource(id = R.string.data_source_api),
            stringResource(id = R.string.data_source_local_file)
        )

//        dataSourceOptions.forEach { option ->
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .selectable(
//                        selected = (selectedDataSource == option),
//                        onClick = {
//                            selectedDataSource = option
//                            onDataSourceChange(option)
//                            if (option == "Local File" || option == "本地文件") {
//                                openFileLauncher.launch(arrayOf("application/json", "text/plain"))
//                            }
//                            coroutineScope.launch {
//                                SettingsDataStore.saveDataSource(context, option)
//                            }
//                        }
//                    )
//                    .padding(8.dp)
//            ) {
//                RadioButton(
//                    selected = (selectedDataSource == option),
//                    onClick = {
//                        selectedDataSource = option
//                        onDataSourceChange(option)
//                        if (option == "Local File" || option == "本地文件") {
//                            openFileLauncher.launch(arrayOf("application/json", "text/plain"))
//                        }
//                        coroutineScope.launch {
//                            SettingsDataStore.saveDataSource(context, option)
//                        }
//                    }
//                )
//                Text(option, modifier = Modifier.padding(start = 8.dp))
//            }
//        }

        dataSourceOptions.forEach { optionLabel ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (labelToDataSourceCode(selectedDataSource) == labelToDataSourceCode(optionLabel)),
                        onClick = {
                            selectedDataSource = optionLabel
                            val standardizedCode = labelToDataSourceCode(optionLabel)
                            onDataSourceChange(standardizedCode)
                            if (standardizedCode == "Local File") {
                                openFileLauncher.launch(arrayOf("application/json", "text/plain"))
                            }
                            coroutineScope.launch {
                                SettingsDataStore.saveDataSource(context, standardizedCode)
                            }
                        }
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = (labelToDataSourceCode(selectedDataSource) == labelToDataSourceCode(optionLabel)),
                    onClick = {
                        selectedDataSource = optionLabel
                        val standardizedCode = labelToDataSourceCode(optionLabel)
                        onDataSourceChange(standardizedCode)
                        if (standardizedCode == "Local File") {
                            openFileLauncher.launch(arrayOf("application/json", "text/plain"))
                        }
                        coroutineScope.launch {
                            SettingsDataStore.saveDataSource(context, standardizedCode)
                        }
                    }
                )
                Text(optionLabel, modifier = Modifier.padding(start = 8.dp))
            }
        }

        // 显示选中的本地文件
        selectedFileUri?.let { uri ->
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.selected_file_label) + " ${uri.lastPathSegment ?: uri.path} ",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


// Dropdown Box 通用组件
@Composable
fun DropdownMenuBox(selectedItem: String, itemList: List<String>, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedItem)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemList.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}

// 预览
@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    MyApplicationTheme {
        SettingsScreen(
            navController = rememberNavController(),
            onThemeChange = {},
            onTemperatureUnitChange = {},
            onDataSourceChange = {},
            onFileSelected = {},
            onLanguageChange = {},
        )
    }
}