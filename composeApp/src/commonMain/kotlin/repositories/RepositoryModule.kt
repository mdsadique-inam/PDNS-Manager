package repositories

import org.koin.dsl.module

val repositoryModule = module {
    single { AuthenticationRepository(get()) }
}