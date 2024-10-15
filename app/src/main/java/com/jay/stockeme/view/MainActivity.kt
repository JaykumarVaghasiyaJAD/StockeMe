package com.jay.stockeme.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jay.stockeme.model.repository.StockRepository
import com.jay.stockeme.viewmodel.StockViewModel
import com.jay.stockeme.viewmodel.StockViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val stockRepository by lazy { StockRepository() }
            val viewModel: StockViewModel by viewModels {
                StockViewModelFactory(stockRepository)
            }
            AppNavigation(viewModel,LocalContext.current)

        }
    }
}

@Composable
 fun AppNavigation(viewModel: StockViewModel, context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(context,navController) }
        composable("signup") { SignupScreen(context, navController) }
        composable("details") { DetailsScreen(viewModel,context) }
    }

}







