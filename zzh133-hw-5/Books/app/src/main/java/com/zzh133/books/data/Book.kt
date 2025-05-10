package com.zzh133.books.data

import androidx.annotation.DrawableRes

data class Book(
//    index, title, author, release date, quote, synopsis and cover image

    val index: Int,
    val title: String,
    val author: String,
    val releaseDate: String,
    val quote: String,
    val synopsis: String,
    @DrawableRes val image: Int,
    val purchaseLink: String // 新增 Amazon 购买链接字段

)

