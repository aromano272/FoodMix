package com.andreromano.foodmix.network.mapper

import android.os.Build
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson


@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class FixImageUrlForEmulator

class FixImageUrlForEmulatorAdapter {
    @FromJson
    @FixImageUrlForEmulator
    fun fromJson(string: String?): String? = string?.let {
        if (Build.FINGERPRINT.contains("generic") && it.startsWith("http://127.0.0.1:8080"))
            it.replaceFirst("http://127.0.0.1:8080", "http://10.0.2.2:8080/")
        else it
    }

    @ToJson
    fun toJson(@FixImageUrlForEmulator value: String): Any = throw UnsupportedOperationException()
}
