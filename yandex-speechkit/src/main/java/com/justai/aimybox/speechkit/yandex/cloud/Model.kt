package com.justai.aimybox.speechkit.yandex.cloud


enum class Language(internal val stringValue: String) { RU("ru-RU"), EN("en-US"), TR("tr-TR") }

enum class VoiceModel(internal val stringValue: String) {
    GENERAL("general"),
    MAPS("maps"),
    DATES("dates"),
    NAMES("names"),
    NUMBERS("numbers")
}

enum class Voice(internal val stringValue: String) {
    ALYSS("alyss"),
    JANE("jane"),
    OKSANA("oksana"),
    OMAZH("omazh"),
    ZAHAR("zahar"),
    ERMIL("ermil")
}

enum class Emotion(internal val stringValue: String) {
    GOOD("good"),
    EVIL("evil"),
    NEUTRAL("neutral")
}

class Speed(val value: Float) {

    companion object {
        const val MAX = 3.0F
        const val MIN = 0.1F
        val DEFAULT = Speed(1.0F)
    }

    internal val floatValue = value.coerceIn(MIN..MAX)
}