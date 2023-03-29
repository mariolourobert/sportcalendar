package fr.mario.sportcalendar.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
fun networkModule(isDebug: Boolean = true) = module {
    single {
        val builder = OkHttpClient.Builder()

        if (isDebug) {
            builder.addInterceptor(HttpLoggingInterceptor())
        }

        builder.build()
    }

    single {
        Json.asConverterFactory("application/json".toMediaType())
    }
}