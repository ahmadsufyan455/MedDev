package com.capstone.meddev.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleResponse(
    val post_title: String,
    val name: String,
    val post_url: String,
    val url: String
) : Parcelable
