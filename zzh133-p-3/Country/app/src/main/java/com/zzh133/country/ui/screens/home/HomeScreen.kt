package com.zzh133.country.ui.screens.home

import HomeViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.JsonObject
import com.zzh133.country.R
import com.zzh133.country.ui.theme.MyApplicationTheme


@Composable
fun HomeScreen(
    navController: NavController,
    selectedTemperatureUnit: String = "°C",
    viewModel: HomeViewModel = HomeViewModel()
) {
    val weather by viewModel.weatherData.observeAsState()


    val noticeOffsetY by animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(durationMillis = 800)
    )

    val noticeAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 800)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.example_country),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.weather_data),
            style = MaterialTheme.typography.headlineSmall
        )

        val jsonObject = weather as? JsonObject
        val currentInfo = jsonObject?.get("current")?.asJsonObject
        val temperatureCelsius = currentInfo?.get("temperature_2m")?.asDouble
        val windSpeed = currentInfo?.get("wind_speed_10m")?.asDouble
        val currentUnits = jsonObject?.get("current_units")?.asJsonObject
        val windSpeedUnit = currentUnits?.get("wind_speed_10m")?.asString ?: ""

        val displayTemperature = if (selectedTemperatureUnit == "°F" && temperatureCelsius != null) {
            (temperatureCelsius * 9 / 5) + 32
        } else {
            temperatureCelsius
        }

        val displayTemperatureUnit = selectedTemperatureUnit

//        Spacer(Modifier.height(12.dp))
//
//        Text(
//            text = stringResource(
//                id = R.string.temperature_display,
//                displayTemperature?.let { String.format("%.1f", it) } ?: "--",
//                displayTemperatureUnit
//            ),
//            style = MaterialTheme.typography.bodyMedium
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        Text(
//            text = stringResource(
//                id = R.string.wind_speed_display,
//                windSpeed?.let { String.format("%.1f", it) } ?: "--",
//                windSpeedUnit
//            ),
//            style = MaterialTheme.typography.bodyMedium
//        )

        Spacer(Modifier.height(12.dp))

        if (displayTemperature != null) {
            val temperatureOffsetY by animateDpAsState(
                targetValue = 0.dp,
                animationSpec = tween(durationMillis = 600)
            )

            Text(
                text = stringResource(
                    id = R.string.temperature_display,
                    displayTemperature.let { String.format("%.1f", it) },
                    displayTemperatureUnit
                ),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.offset(y = temperatureOffsetY + 20.dp) // 初始偏移20dp
            )
        }

        Spacer(Modifier.height(8.dp))

        if (windSpeed != null) {
            val windSpeedOffsetY by animateDpAsState(
                targetValue = 0.dp,
                animationSpec = tween(durationMillis = 600)
            )

            Text(
                text = stringResource(
                    id = R.string.wind_speed_display,
                    windSpeed.let { String.format("%.1f", it) },
                    windSpeedUnit
                ),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.offset(y = windSpeedOffsetY + 20.dp) // 初始偏移20dp
            )
        }

        Spacer(Modifier.height(24.dp))

        val noticeOffsetY by animateDpAsState(
            targetValue = 0.dp,
            animationSpec = tween(durationMillis = 800)
        )

        Text(
            text = stringResource(id = R.string.api_data_language_note),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .offset(y = noticeOffsetY + 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MyApplicationTheme {
        HomeScreen(
            navController = rememberNavController(),
            selectedTemperatureUnit = "°C"
        )
    }
}
