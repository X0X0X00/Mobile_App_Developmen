package com.zzh133.country.ui.parts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh133.country.R
import com.zzh133.country.ui.theme.MyApplicationTheme

@Composable
fun CountryBar(
    initialCountry: String,
    onSearch: (String) -> Unit,
) {
    var currentInput by remember { mutableStateOf(initialCountry) }
    var buttonClicked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .animateContentSize()  // ✨ 加一点整体变化时的流畅动画
    ) {
        TextField(
            modifier = Modifier.weight(4f),
            value = currentInput,
            onValueChange = { currentInput = it },
            label = { Text(stringResource(id = R.string.enter_country_hint)) } // ✨多语言
        )
        Spacer(Modifier.size(12.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                buttonClicked = true
                onSearch(currentInput)
            }
        ) {
            Text(
                text = if (buttonClicked) stringResource(id = R.string.going) else stringResource(id = R.string.go)  // ✨动态变化文本
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryBar() {
    MyApplicationTheme {
        CountryBar(
            initialCountry = "Canada",
            onSearch = { /* Do nothing */ }
        )
    }
}
