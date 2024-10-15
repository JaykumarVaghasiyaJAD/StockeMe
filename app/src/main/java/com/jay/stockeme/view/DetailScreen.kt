package com.jay.stockeme.view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jay.stockeme.Constant
import com.jay.stockeme.viewmodel.StockViewModel

@Composable
fun DetailsScreen(viewModel: StockViewModel, context: Context) {
    val historicalData = viewModel.historicalData.collectAsState()
    val livePrice = viewModel.livePrice.collectAsState()
    // Fetch historical data and connect to live prices
    val apiKey = Constant.API_KEY
    val ticker = "AAPL"
    viewModel.fetchHistoricalData(ticker, "2024-10-08", "2024-10-15", apiKey)
    viewModel.connectToLivePrices(apiKey, ticker)

    // Example of displaying live and historical data
    Column {
    Text(text = "Live Price: ${livePrice.value?.price ?: "Loading..."}")
    historicalData.value.forEach { data ->
        Text(text = "Date: ${data.date} - Close: ${data.close}")
    }
    }

}

