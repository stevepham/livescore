package com.ht117.livescore.ext

import java.text.SimpleDateFormat

fun String.formatDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = sdf.parse(this)
    val format = SimpleDateFormat("yyyy-MM-dd")
    val timeFmt = SimpleDateFormat("HH:mm")
    return date?.let {
        "${format.format(it)}\n${timeFmt.format(it)}"
    }?: this
}

fun String.getTime(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = sdf.parse(this)

    return date?.let {
        it.time
    } ?: (System.currentTimeMillis() + 60 * 1_000 * 2)
}
