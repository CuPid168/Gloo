package com.dicoding.gloo.activity.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val imageUrl: String,
    val title: String,
    val source: String,
    val date: String,
    val description: String,
    val photoDescription: String
) : Parcelable
