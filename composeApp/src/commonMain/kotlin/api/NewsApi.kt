package api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines/category/health/in.json")
    suspend fun fetch(): NewsDto?
}

val RetrofitNewsApi: NewsApi by lazy {
    RetrofitClient.create()
}

private val RetrofitClient by lazy {
    Retrofit.Builder()
        .baseUrl("https://saurav.tech/NewsAPI/")
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .build()
}