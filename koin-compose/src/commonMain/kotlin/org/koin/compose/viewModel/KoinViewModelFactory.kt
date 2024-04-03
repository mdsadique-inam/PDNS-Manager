package org.koin.compose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.compose.parameters.ComposeParametersHolder
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass


/**
 * ViewModelProvider.Factory for Koin instances resolution
 * @see ViewModelProvider.Factory
 */
class KoinViewModelFactory(
    private val kClass: KClass<out ViewModel>,
    private val scope: Scope,
    private val qualifier: Qualifier? = null,
    private val params: ParametersDefinition? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val androidParams = ComposeParametersHolder(params, extras)
        return scope.get(kClass, qualifier) { androidParams }
    }
}