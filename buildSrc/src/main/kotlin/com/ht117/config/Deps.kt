package com.ht117.config

object Deps {

    object App {
        const val ID = "com.ht117.livescore"
        const val minSdk = 24
        const val compileSdk = 33
        const val targetSdk = 33
        const val versionCode = 1
        const val versionName = "1.0"
        const val namespace = "com.ht117.livescore"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:2.5.3"
        const val navUiKtx = "androidx.navigation:navigation-ui-ktx:2.5.3"
        const val roomRuntime = "androidx.room:room-runtime:2.5.0"
        const val roomCompiler = "androidx.room:room-compiler:2.5.0"
        const val roomKtx = "androidx.room:room-ktx:2.5.0"
    }

    object Common {
        const val material = "com.google.android.material:material:1.8.0"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
        const val exoCore = "com.google.android.exoplayer:exoplayer-core:2.18.3"
        const val exoUI = "com.google.android.exoplayer:exoplayer-ui:2.18.3"
        const val exoStreaming = "com.google.android.exoplayer:exoplayer-smoothstreaming:2.18.3"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:3.2.2"
        const val android = "io.insert-koin:koin-android:3.2.2"
    }

    object Networks {
        const val ktxSerializeCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.20"
        const val ktorCore = "io.ktor:ktor-client-core:2.1.3"
        const val ktorOkhttp = "io.ktor:ktor-client-okhttp:2.1.3"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
        const val ktorContent = "io.ktor:ktor-client-content-negotiation:2.1.3"
        const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:2.1.3"
        const val coil = "io.coil-kt:coil:2.2.2"
    }

    object Test {
        const val jUnit = "junit:junit:4.13.2"
        const val extJUnit = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        const val mockk = "io.mockk:mockk-android:1.13.4"
        const val mockAgent = "io.mockk:mockk-agent:1.13.4"
        const val turbine = "app.cash.turbine:turbine:0.12.1"

//        const val ktor = "io.ktor:ktor-client-mock"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    }
}
