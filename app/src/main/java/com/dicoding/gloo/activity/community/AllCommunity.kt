package com.dicoding.gloo.activity.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllCommunity(
    val allPhoto: String,
    val allName: String,
    val allTime: String,
    val allPost: String,
    val allLike: String,
    val allComment: String
) : Parcelable
