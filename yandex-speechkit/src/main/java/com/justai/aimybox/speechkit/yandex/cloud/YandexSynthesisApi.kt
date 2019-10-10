package com.justai.aimybox.speechkit.yandex.cloud

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


internal class YandexSynthesisApi(
    private val yandexPassportOAuthKey: String,
    private val folderId: String
) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    suspend fun request(
        text: String,
        language: Language,
        config: YandexTextToSpeech.Config
    ): ByteArray {
        val token = IAmTokenGenerator.getOAuthToken(yandexPassportOAuthKey)

        val requestUrl = config.apiUrl.toHttpUrl().newBuilder()
            .addQueryParameter("folderId", folderId)
            .addQueryParameter("text", text)
            .addQueryParameter("lang", language.stringValue)
            .addQueryParameter("voice", config.voice.stringValue)
            .addQueryParameter("emotion", config.emotion.stringValue)
            .addQueryParameter("speed", config.speed.floatValue.toString())
            .addQueryParameter("format", "oggopus")
            .addQueryParameter("sampleRateHertz", config.sampleRate.intValue.toString())
            .build()
            .toString()

        val request = Request.Builder()
            .url(requestUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return suspendCancellableCoroutine<InputStream> { continuation ->
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    L.e("Exception occurred during API request. Request: $call")
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (body == null) {
                        continuation.resumeWithException(NullPointerException("Body is null"))
                    } else {
                        continuation.resume(body.byteStream())
                    }
                }
            })
        }.use(InputStream::readBytes)
    }
}