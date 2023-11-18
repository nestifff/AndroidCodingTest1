package com.example.androidcodingtest1.app.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val id: String,
    val text: String,
    val color: Int
): Parcelable
