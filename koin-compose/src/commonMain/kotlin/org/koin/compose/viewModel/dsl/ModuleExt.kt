package org.koin.compose.viewModel.dsl

import androidx.lifecycle.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier


/**
 * ViewModel DSL Extension
 * Allow to declare a ViewModel - be later inject into Activity/Fragment with dedicated injector
 *
 * @author Arnaud Giuliani
 *
 * @param qualifier - definition qualifier
 * @param definition - allow definition override
 */
inline fun <reified T : ViewModel> Module.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}