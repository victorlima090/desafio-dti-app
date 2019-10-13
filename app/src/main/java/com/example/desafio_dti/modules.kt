package com.example.desafio_dti


import com.example.desafio_dti.login.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { LoginViewModel(androidApplication()) }
}
