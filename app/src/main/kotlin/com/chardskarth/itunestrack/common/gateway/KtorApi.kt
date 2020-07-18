package com.chardskarth.itunestrack.common.gateway

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


interface KtorApi {
    val baseUrl: String

    fun createClient(httpBuilderFeatureConfig: HttpBuilderFeatureConfig) = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json(
                    JsonConfiguration(
                        isLenient = true,
                        ignoreUnknownKeys = true
                    )
                )
            )
            httpBuilderFeatureConfig.jsonFeatureConfigBlock?.let { this.it() }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            httpBuilderFeatureConfig.httpTimeoutConfigBlock?.let { this.it() }
        }
    }

    fun encodeUrl(toEncode: String): String = URLEncoder.encode(toEncode, "utf-8")

}