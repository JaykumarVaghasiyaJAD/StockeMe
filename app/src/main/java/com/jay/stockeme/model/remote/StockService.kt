package com.jay.stockeme.model.remote

import com.jay.stockeme.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TiingoApiService {
    @GET("tiingo/daily/{ticker}/prices")
    suspend fun getHistoricalData(
        @Path("ticker") ticker: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("token") apiKey: String
    ): List<StockData>
}

object SpinnerInstance {

    val api: TiingoApiService by lazy {
        RetrofitInstance.api.create(
            TiingoApiService::class.java
        )
    }
}

object RetrofitInstance {

    private val client: OkHttpClient? = OkHttpClient.Builder().build()
    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client!!)
            .build()
    }
}

data class StockData(
    val date: String,
    val close: Double,
    val high: Double,
    val low: Double,
    val open: Double,
    val volume: Int
)

data class LiveStockPrice(
    val ticker: String,
    val price: Double
)