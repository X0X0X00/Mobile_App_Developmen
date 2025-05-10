package com.zzh133.country.ui.screens.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.JsonObject
import com.zzh133.country.ui.parts.CountryBar
import com.zzh133.country.ui.theme.MyApplicationTheme

@Composable
fun CountryListScreen(
    navController: NavController,
    viewModel: CountryListViewModel,
    selectedDataSource: String,
    selectedFileUri: String?
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val countryList by viewModel.countries.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }

    // 🌟 动态决定用API还是Local File
    androidx.compose.runtime.LaunchedEffect(selectedDataSource, selectedFileUri) {
        if (selectedDataSource == "Local File" && selectedFileUri != null) {
            viewModel.loadCountriesFromUri(context, selectedFileUri)
        } else {
            viewModel.loadCountriesFromApi()
        }
    }

    val filteredList = countryList.filter { country ->
        val countryName = getCountryName(country)
        countryName.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        CountryBar(
            initialCountry = searchQuery,
            onSearch = { searchQuery = it }
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(filteredList) { country ->
                val countryName = getCountryName(country)
                ListItem(
                    headlineContent = { Text(text = countryName) },
                    modifier = Modifier.clickable {
                        navController.navigate("detail/${countryName}")
                    }
                )
                Divider()
            }
        }
    }
}

private fun getCountryName(country: Any): String {
    return try {
        val jsonObject = country as? JsonObject
        jsonObject?.get("name")?.asJsonObject?.get("common")?.asString ?: ""
    } catch (e: Exception) {
        ""
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryListScreen() {
    MyApplicationTheme {
        CountryListScreen(
            navController = rememberNavController(),
            viewModel = CountryListViewModel(),
            selectedDataSource = "API", // 预览用
            selectedFileUri = null // 预览用
        )
    }
}
