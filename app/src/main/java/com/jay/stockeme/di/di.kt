package com.jay.stockeme.di

import com.jay.stockeme.model.repository.AuthenticationRepository
import com.jay.stockeme.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AuthenticationRepository() }
    viewModel { AuthViewModel(get()) }


}