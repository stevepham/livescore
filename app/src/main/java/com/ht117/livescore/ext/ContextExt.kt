package com.ht117.livescore.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

tailrec fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity")
    }
}
