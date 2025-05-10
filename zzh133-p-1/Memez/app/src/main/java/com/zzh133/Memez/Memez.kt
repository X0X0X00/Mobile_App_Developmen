package com.zzh133.Memez


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.ContentValues
import android.provider.MediaStore
import android.os.Environment
import android.graphics.Bitmap


class Memez : ViewModel() {

    var memeImageIndex by mutableStateOf(0)
        private set
    var memeTextIndex by mutableStateOf(0)
        private set

    var totalMemes by mutableStateOf(1)
        private set

    //  not displayed in the UI
    var nextMemeIndex by mutableStateOf(0)

    var nextTotalMemes by mutableStateOf(1)

    var cleared by mutableStateOf(false)

    var nextImageIndex by mutableStateOf(0)
    var nextTextIndex by mutableStateOf(0)


    val imageList = arrayOf(
        R.drawable._40,
        R.drawable._41,
        R.drawable._42,
        R.drawable._43,
        R.drawable._44,
        R.drawable._45,
        R.drawable._46,
        R.drawable._47,
    )

    val stringList = arrayOf(
        R.string._40,
        R.string._41,
        R.string._42,
        R.string._43,
        R.string._44,
        R.string._45,
        R.string._46,
        R.string._47,
    )

    fun displayNext() {
        memeImageIndex = nextImageIndex
        memeTextIndex = nextTextIndex
        totalMemes = nextTotalMemes
    }

    fun nextMeme() {
//        only do assign but not change the display
        nextImageIndex = (0..imageList.size-1).random()
        nextTextIndex = (0..stringList.size-1).random()
        if (!cleared) {
            nextTotalMemes = totalMemes + 1
        }
        displayNext()
        cleared = false

    }

    fun clearMeme() {
//        Clearing the meme counter does NOT generate a new meme or refresh the screen
        nextMemeIndex = 0
        nextTotalMemes = 1
        cleared = true
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun downLoadMeme(context: Context) {
        // 获取当前 meme 的资源 ID
        val memeImageId = imageList[memeImageIndex]

        // 获取 Bitmap
        val drawable = ContextCompat.getDrawable(context, memeImageId) as BitmapDrawable
        val bitmap = drawable.bitmap

        // 启动协程以避免在主线程中进行耗时操作
        GlobalScope.launch(Dispatchers.IO) {
            // 创建文件名
            val filename = "Meme_${System.currentTimeMillis()}.png"

            // 保存到 MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyMemes") // 自定义文件夹
            }

            // 插入到 MediaStore
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // 压缩并保存
                }
            }
        }
    }
}
