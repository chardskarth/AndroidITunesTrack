package com.chardskarth.itunestrack.common.gateway

interface HttpBuilderFeatureConfig {
    val jsonFeatureConfigBlock: JsonFeatureConfigBlock
    val httpTimeoutConfigBlock: HttpTimeoutConfigBlock
}
