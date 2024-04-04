package ui.viewModels

import org.koin.dsl.module

val viewModelModule = module {
    factory { LoginViewModel(get()) }
}