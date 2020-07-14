import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.gradle.kotlin.dsl.version
import org.gradle.kotlin.dsl.apply

object Versions {
    const val kotlin = "1.3.72"
}

object BuildPlugins {
    const val androidBuildToolsVersion = "4.2.0-alpha03"

    const val androidGradlePlugin = "com.android.tools.build:gradle:$androidBuildToolsVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val androidApplication = "com.android.application"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExts = "kotlin-android-extensions"
}

object Kotlin {
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val xSerialization = "org.jetbrains.kotlin.plugin.serialization"

    const val xSerializationRuntime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0"
}

fun PluginDependenciesSpec.xSerializationPlugin(): PluginDependencySpec {
    return id(Kotlin.xSerialization) version Versions.kotlin
}


object Ktor {
    private const val ktorVersion = "1.3.2"

    const val clientCio = "io.ktor:ktor-client-cio:$ktorVersion"
    const val ktxSerializerJvm = "io.ktor:ktor-client-serialization-jvm:$ktorVersion"
    const val loggingJvm = "io.ktor:ktor-client-logging-jvm:$ktorVersion"
}

object AndroidSdk {
    const val min = 23
    const val compile = 29
    const val target = 29
    const val buildTools = "29.0.2"
}

object Android {
    private const val materialVersion = "1.1.0"
    private const val lifecycleVersion = "2.2.0"
    private const val pagingVersion = "2.1.2"

    const val xAppCompat = "androidx.appcompat:appcompat:1.1.0"
    const val xCore = "androidx.core:core-ktx:1.3.0"
    const val xConstraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val xLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val xLifecycleLiveData =  "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val xTestExt = "androidx.test.ext:junit:1.1.1"

    const val xTestEspressoCore = "androidx.test.espresso:espresso-core:3.2.0"

    const val xRecyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val xPagingRuntime = "androidx.paging:paging-runtime:$pagingVersion"
    const val xPagingRuntimeKtx = "androidx.paging:paging-runtime-ktx:2.1.2"

    const val material = "com.google.android.material:material:$materialVersion"

    object Glide {
        private const val version = "4.11.0"

        const val glide = "com.github.bumptech.glide:glide:$version"
        const val glideProcessor = "com.github.bumptech.glide:compiler:$version"
    }

}

object Koin {
    private const val version = "2.1.6"

    const val android = "org.koin:koin-android:$version"
    const val androidViewModel = "org.koin:koin-android-viewmodel:$version"
}

object Testing {
    private const val junitVersion = "4.13"

    const val junitAndroidRunnerName = "androidx.test.runner.AndroidJUnit.Runner"
    const val androidXTestEspresso = "androidx.test.espresso:espresso-core:3.2.0"

    const val junit = "junit:junit:$junitVersion"
}
