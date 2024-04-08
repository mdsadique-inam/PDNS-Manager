package ui.viewModels

import org.koin.compose.viewModel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}