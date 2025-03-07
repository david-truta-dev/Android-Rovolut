package com.tdavidc.dev.utilities.extensions

import android.content.res.Resources
import androidx.annotation.IntegerRes

fun Resources.getDuration(@IntegerRes id: Int): Long {
    return this.getInteger(id).toLong()
}