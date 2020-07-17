package com.chardskarth.itunestrack.common.gateway

import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature

typealias JsonFeatureConfigBlock = (JsonFeature.Config.() -> Unit)?
typealias HttpTimeoutConfigBlock = (HttpTimeout.HttpTimeoutCapabilityConfiguration.() -> Unit)?