package com.ht117.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Remote {
    const val Host = "https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com"

    private val jsonConfig = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    fun getClient() = HttpClient(OkHttp) {
        followRedirects = true
        install(ContentNegotiation) {
            json(jsonConfig)
        }
    }
}
