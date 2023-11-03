package com.pictures.presentation.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {
    object GlideProgressBar {
        fun getCircularProgressDrawable(context: Context) =
            CircularProgressDrawable(context).apply {
                setColorSchemeColors(0xFF000000.toInt())
                strokeWidth = 15f
                centerRadius = 50f
                start()
            }
    }
}