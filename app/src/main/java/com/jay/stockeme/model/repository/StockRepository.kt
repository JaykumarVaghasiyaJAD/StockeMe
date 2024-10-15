package com.jay.stockeme.model.repository


import com.jay.stockeme.model.remote.LiveStockPrice
import com.jay.stockeme.model.remote.SpinnerInstance
import com.jay.stockeme.model.remote.StockData
import com.jay.stockeme.model.remote.TiingoApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class StockRepository() {

    // Flow for live stock price updates
    private val _livePrice = MutableStateFlow<LiveStockPrice?>(null)
    val livePrice = _livePrice.asStateFlow()

    // Fetch historical stock data
    suspend fun getHistoricalData(
        ticker: String,
        startDate: String,
        endDate: String,
        apiKey: String
    ): List<StockData> {
        return SpinnerInstance.api.getHistoricalData(ticker, startDate, endDate, apiKey)
    }

    // WebSocket for live updates
    private val client = OkHttpClient()

    fun connectToLivePrices(apiKey: String, ticker: String) {
        val request = Request.Builder()
            .url("wss://api.tiingo.com/iex")
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                val subscribeMessage = """
                    {
                        "eventName": "subscribe",
                        "authorization": "$apiKey",
                        "eventData": {
                            "thresholdLevel": 2,
                            "tickers": ["$ticker"]
                        }
                    }
                """.trimIndent()
                webSocket.send(subscribeMessage)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {

                val livePriceData = parseLivePrice(text)
                _livePrice.value = livePriceData
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                t.printStackTrace()
            }
        }

        client.newWebSocket(request, listener)
    }

    private fun parseLivePrice(response: String): LiveStockPrice {
        val ticker = "AAPL"
        val price = 150.2
        return LiveStockPrice(ticker, price)
    }
}
