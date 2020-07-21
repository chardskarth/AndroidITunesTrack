package com.chardskarth.itunestrack.base.gateway

import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature

typealias JsonFeatureConfigBlock = (JsonFeature.Config.() -> Unit)?
typealias HttpTimeoutConfigBlock = (HttpTimeout.HttpTimeoutCapabilityConfiguration.() -> Unit)?

interface HttpBuilderFeatureConfig {
    val jsonFeatureConfigBlock: JsonFeatureConfigBlock
    val httpTimeoutConfigBlock: HttpTimeoutConfigBlock
}
