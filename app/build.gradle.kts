
val self = this

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExts)

    xSerializationPlugin()
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.buildTools)
    defaultConfig {
        applicationId(ApplicationConfig.applicationId)
        versionCode(ApplicationConfig.versionCode)
        versionName(ApplicationConfig.versionName)

        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        testInstrumentationRunner(Testing.junitAndroidRunnerName)
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(Kotlin.stdLib)
    implementation(Kotlin.xSerializationRuntime)

    implementation(Android.xCore)
    implementation(Android.xAppCompat)
    implementation(Android.material)
    implementation(Android.xConstraintLayout)
    implementation(Android.xLifecycleLiveData)
    implementation(Android.xLifecycleViewModel)
    implementation(Android.xPagingRuntime)
    implementation(Android.xPagingRuntimeKtx)

    implementation(Android.Glide.glide)

    implementation(Ktor.clientCio)
    implementation(Ktor.ktxSerializerJvm)
    implementation(Ktor.loggingJvm)

    implementation(Koin.android)
    implementation(Koin.androidViewModel)

    testImplementation(Testing.junit)
    androidTestImplementation(Android.xTestExt)
    androidTestImplementation(Android.xTestEspressoCore)
}