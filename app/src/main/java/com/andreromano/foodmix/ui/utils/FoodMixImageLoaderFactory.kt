package com.andreromano.foodmix.ui.utils

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy

class FoodMixImageLoaderFactory(
    private val context: Context
) : ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(context)
//            .memoryCachePolicy(CachePolicy.DISABLED)
//            .diskCachePolicy(CachePolicy.DISABLED)
//            .networkCachePolicy(CachePolicy.DISABLED)
            // TODO: Include placeholder
//                .placeholder()
            .build()
}