package com.jay.stockeme.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jay.stockeme.model.remote.LiveStockPrice
import com.jay.stockeme.model.remote.StockData
import com.jay.stockeme.model.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    // Expose historical data as StateFlow
    private val _historicalData = MutableStateFlow<List<StockData>>(emptyList())
    val historicalData: StateFlow<List<StockData>> = _historicalData

    // Expose live stock price as StateFlow
    val livePrice: StateFlow<LiveStockPrice?> = repository.livePrice

    // Fetch historical data
    fun fetchHistoricalData(ticker: String, startDate: String, endDate: String, apiKey: String) {
        viewModelScope.launch {
            val data = repository.getHistoricalData(ticker, startDate, endDate, apiKey)
            _historicalData.value = data
        }
    }

    // Connect to WebSocket for live updates
    fun connectToLivePrices(apiKey: String, ticker: String) {
        repository.connectToLivePrices(apiKey, ticker)
    }
}

class StockViewModelFactory(
    private val stockRepository: StockRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(stockRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
