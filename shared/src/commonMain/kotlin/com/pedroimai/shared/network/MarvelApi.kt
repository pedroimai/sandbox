package com.pedroimai.shared.network

import com.pedroimai.shared.domain.CharactersPayload
import com.soywiz.krypto.MD5
import com.soywiz.krypto.encoding.ASCII
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

interface MarvelApi {
    suspend fun getCharacters(): CharactersPayload

    class Impl : MarvelApi {
        override suspend fun getCharacters(): CharactersPayload =
            client.get {
                marvel("characters")
                parameter("limit", 1)
            }

        private fun HttpRequestBuilder.marvel(path: String) {
            url {
                protocol = URLProtocol.HTTPS
                host = "gateway.marvel.com"
                path("v1", "public", path)
                parameters.append("ts", ts.toString())
                parameters.append("apikey", API_KEY)
                parameters.append("hash", hash)
            }
        }

        private val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }

        companion object {
            private const val API_KEY = "{your api key}"
            private const val PRIVATE_KEY = "{your private key}"
            private val ts = Clock.System.now().toEpochMilliseconds()

            private val hash: String
                get() =
                    MD5.digest(
                        ASCII(
                            StringBuilder()
                                .append(ts)
                                .append(PRIVATE_KEY)
                                .append(API_KEY)
                                .toString()
                        )
                    ).hex
        }
    }
}
