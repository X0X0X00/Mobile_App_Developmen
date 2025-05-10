package com.zzh133.country.ui.screens.country

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zzh133.country.R
import com.zzh133.country.ui.theme.MyApplicationTheme

@Composable
fun CountryDetailScreen(
    navController: NavController,
    countryName: String,
    viewModel: CountryDetailViewModel = viewModel()
) {
    val country by viewModel.country.observeAsState()

    LaunchedEffect(key1 = countryName) {
        viewModel.fetchCountry(countryName)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.details_for_country, countryName),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        country?.let {
            val name = it.get("name")?.asJsonObject?.get("common")?.asString ?: "Unknown"
            val capital = it.get("capital")?.asJsonArray?.get(0)?.asString ?: "Unknown"
            val region = it.get("region")?.asString ?: "Unknown"
            val population = it.get("population")?.asInt ?: 0
            val currencies = it.get("currencies")?.asJsonObject?.entrySet()?.joinToString { entry ->
                "${entry.key} (${entry.value.asJsonObject.get("name")?.asString})"
            } ?: "Unknown"

            Text(text = stringResource(id = R.string.country_label, name))
            Text(text = stringResource(id = R.string.capital_label, capital))
            Text(text = stringResource(id = R.string.region_label, region))
            Text(text = stringResource(id = R.string.population_label, population))
            Text(text = stringResource(id = R.string.currencies_label, currencies))
        } ?: run {
            Text(text = stringResource(id = R.string.loading_country_details))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryDetailScreen() {
    MyApplicationTheme {
        CountryDetailScreen(
            navController = rememberNavController(),
            countryName = "United States"
        )
    }
}
