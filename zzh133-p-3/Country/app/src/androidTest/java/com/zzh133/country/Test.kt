package com.zzh133.country

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.zzh133.country.ui.data.api.CountryClient
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader

@RunWith(AndroidJUnit4::class)
class Test {

    companion object {
        private const val TAG = "ApiInstrumentedTest"
    }

    // 测试1：读取本地json并打印
    @Test
    fun readLocalJsonAndLog() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        try {
            val assetManager = context.assets
            val jsonFiles = assetManager.list("json")
            jsonFiles?.forEach {
                Log.d("AssetTest", "Asset file in json/: $it")
            }
            val inputStream = context.assets.open("all.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val content = reader.readText()
            Log.d(TAG, "Read from local JSON: $content")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Failed to read local JSON file.")
        }
    }

    // 测试2：调用CountryClient.api访问API并打印
    @Test
    fun callApiAndLogResponse() {
        try {
            // ✨ 直接使用项目中封装好的 CountryClient.api
            val response = CountryClient.api.fetchCountries().execute()

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "API Response:\n$body")
            } else {
                Log.e(TAG, "API call failed: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "API call failed with exception.")
        }
    }
}
