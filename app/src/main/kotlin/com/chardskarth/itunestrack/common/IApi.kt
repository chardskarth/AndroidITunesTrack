package com.chardskarth.itunestrack.common

import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

import java.net.URLEncoder

typealias JsonFeatureConfigBlock = JsonFeature.Config.() -> Unit

interface IApi {
    val baseUrl: String
    var apiResultCallback: IApiResultCallback?

    fun createClient(jsonFeatureConfigBlock: JsonFeatureConfigBlock? = null) = HttpClient() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json(
                    JsonConfiguration(
                        isLenient = true,
                        ignoreUnknownKeys = true
                    )
                )
            )
            jsonFeatureConfigBlock?.let {
                this.jsonFeatureConfigBlock()
            }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
    }

    fun encodeUrl(toEncode: String): String = URLEncoder.encode(toEncode, "utf-8")

}