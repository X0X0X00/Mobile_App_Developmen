package com.zzh133.country

//import com.zzh133.country.ui.screens.home.CountryListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zzh133.country.data.SettingsDataStore
import com.zzh133.country.ui.screens.country.CountryDetailScreen
import com.zzh133.country.ui.screens.country.CountryListScreen
import com.zzh133.country.ui.screens.country.CountryListViewModel
import com.zzh133.country.ui.screens.home.HomeScreen
import com.zzh133.country.ui.screens.info.InfoScreen
import com.zzh133.country.ui.screens.settings.SettingsScreen
import com.zzh133.country.ui.screens.settings.updateLocale
import com.zzh133.country.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        // 获取并设置语言
        val savedLanguage = SettingsDataStore.getLanguage(context)
        updateLocale(context, savedLanguage)

        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            // 同步读取保存的设置
            val savedTheme = remember { SettingsDataStore.getTheme(context) }
            val savedTemperatureUnit = remember { SettingsDataStore.getTemperatureUnit(context) }
            val savedDataSource = remember { SettingsDataStore.getDataSource(context) }
            val savedLanguage = remember { SettingsDataStore.getLanguage(context) } // ✨新增

            // 用 rememberSaveable 保存 UI 状态
            var selectedTheme by rememberSaveable { mutableStateOf(savedTheme) }
            var selectedTemperatureUnit by rememberSaveable { mutableStateOf(savedTemperatureUnit) }
            var selectedDataSource by rememberSaveable { mutableStateOf(savedDataSource) }
            var selectedLanguage by rememberSaveable { mutableStateOf(savedLanguage) } // ✨新增
            var selectedFileUri by rememberSaveable { mutableStateOf<String?>(null) }

            // Theme 包裹全局界面
            MyApplicationTheme(selectedTheme = selectedTheme) {
                val navController = rememberNavController()
                CountryAppNavHost(
                    navController = navController,
                    onThemeChange = { selectedTheme = it },
                    onTemperatureUnitChange = { selectedTemperatureUnit = it },
                    onDataSourceChange = { selectedDataSource = it },
                    onLanguageChange = { selectedLanguage = it },
                    onFileSelected = { uri -> selectedFileUri = uri?.toString() },
                    selectedTemperatureUnit = selectedTemperatureUnit,
                    selectedDataSource = selectedDataSource,
                    selectedLanguage = selectedLanguage,
                    selectedFileUri = selectedFileUri
                )
            }
        }
    }
}


@Composable
fun CountryAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onThemeChange: (String) -> Unit,
    onTemperatureUnitChange: (String) -> Unit,
    onDataSourceChange: (String) -> Unit,
    onFileSelected: (android.net.Uri?) -> Unit,
    onLanguageChange: (String) -> Unit,
    selectedTemperatureUnit: String,
    selectedDataSource: String,
    selectedFileUri: String?,
    selectedLanguage: String?,
) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Settings,
        BottomNavItem.Info
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label()) },
                        label = { Text(item.label()) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    navController = navController,
                    selectedTemperatureUnit = selectedTemperatureUnit
                )
            }
            composable("list") {
                val viewModel: CountryListViewModel = viewModel()
                CountryListScreen(
                    navController = navController,
                    viewModel = viewModel,
                    selectedDataSource = selectedDataSource,
                    selectedFileUri = selectedFileUri
                )
            }
            composable("detail/{countryName}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("countryName") ?: ""
                CountryDetailScreen(navController = navController, countryName = name)
            }
            composable("settings") {
                SettingsScreen(
                    navController = navController,
                    onThemeChange = onThemeChange,
                    onTemperatureUnitChange = onTemperatureUnitChange,
                    onDataSourceChange = onDataSourceChange,
                    onFileSelected = onFileSelected,
                    onLanguageChange = onLanguageChange,
                )
            }
            composable("info") {
                InfoScreen(navController)
            }
        }
    }
}


sealed class BottomNavItem(val route: String, @StringRes val labelResId: Int, val icon: ImageVector) {
    object Home : BottomNavItem("home", R.string.home, Icons.Default.Home)
    object List : BottomNavItem("list", R.string.countries, Icons.Default.Menu)
    object Settings : BottomNavItem("settings", R.string.settings, Icons.Default.Settings)
    object Info : BottomNavItem("info", R.string.info, Icons.Default.Info)

    @Composable
    fun label(): String {
        return stringResource(id = labelResId)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}