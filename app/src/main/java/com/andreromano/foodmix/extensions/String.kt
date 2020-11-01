package com.andreromano.foodmix.extensions

import java.text.Normalizer
import java.util.regex.Pattern

fun String.withoutAccents(): String {
    val nfdNormalizedString = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(nfdNormalizedString).replaceAll("")
}

fun String?.orNull() = if (this.isNullOrBlank()) null else this

fun String.normalizeToAscii() = Normalizer.normalize(this, Normalizer.Form.NFD).replace("\\p{Mn}".toRegex(), "")
