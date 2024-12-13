package com.dicoding.gloo.activity.community

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCommunity(
    val myTime: String,
    val myPost: String,
    val myLike: String,
    val myComment: String
) : Parcelable
