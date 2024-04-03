package org.koin.compose.viewModel

import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass


/**
 * Resolve a ViewModel instance
 *
 * @param vmClass
 * @param viewModelStore
 * @param key
 * @param extras - @see CreationExtras
 * @param qualifier
 * @param scope
 * @param parameters - for instance building injection
 */
fun <T : ViewModel> resolveViewModel(
    vmClass: KClass<T>,
    viewModelStore: ViewModelStore,
    key: String? = null,
    extras: CreationExtras,
    qualifier: Qualifier? = null,
    scope: Scope,
    parameters: ParametersDefinition? = null,
): T {
    val factory = KoinViewModelFactory(vmClass, scope, qualifier, parameters)
    val provider = ViewModelProvider.create(viewModelStore, factory, extras)
    val vmKey = getViewModelKey(qualifier, scope, key)
    return when {
        vmKey != null -> provider[vmKey, vmClass]
        else -> provider[vmClass]
    }
}

internal fun getViewModelKey(qualifier: Qualifier?, scope: Scope, key: String?): String? {
    return if (qualifier == null && key == null && scope.isRoot) {
        null
    } else {
        val q = qualifier?.value ?: ""
        val k = key ?: ""
        val s = if (!scope.isRoot) scope.id else ""
        "$q$k$s"
    }
}

@Composable
fun defaultExtras(viewModelStoreOwner: ViewModelStoreOwner): CreationExtras = when {
    viewModelStoreOwner is HasDefaultViewModelProviderFactory -> viewModelStoreOwner.defaultViewModelCreationExtras
    else -> CreationExtras.Empty
}