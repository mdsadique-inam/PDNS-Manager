package org.koin.compose.viewModel

import androidx.compose.runtime.Composable
import androidx.core.bundle.Bundle
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.savedstate.SavedStateRegistryOwner
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope


/**
 * Resolve ViewModel instance with Navigation NavBackStackEntry as extras parameters
 *
 * @param qualifier
 * @param parameters
 *
 */
@Composable
inline fun <reified VM : ViewModel> koinNavViewModel(
    qualifier: Qualifier? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    extras: CreationExtras = defaultNavExtras(viewModelStoreOwner),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): VM {
    return resolveViewModel(
        VM::class, viewModelStoreOwner.viewModelStore, key, extras, qualifier, scope, parameters
    )
}


@Composable
fun defaultNavExtras(viewModelStoreOwner: ViewModelStoreOwner): CreationExtras = when {
    //TODO To be fully verified
    viewModelStoreOwner is NavBackStackEntry && viewModelStoreOwner.arguments != null -> viewModelStoreOwner.arguments?.toExtras(viewModelStoreOwner) ?: CreationExtras.Empty
    viewModelStoreOwner is HasDefaultViewModelProviderFactory -> viewModelStoreOwner.defaultViewModelCreationExtras
    else -> CreationExtras.Empty
}

fun Bundle.toExtras(viewModelStoreOwner: ViewModelStoreOwner): CreationExtras? {
    return if (keySet().isEmpty()) null
    else {
        runCatching {
            MutableCreationExtras().also { extras ->
                extras[DEFAULT_ARGS_KEY] = this
                extras[VIEW_MODEL_STORE_OWNER_KEY] = viewModelStoreOwner
                extras[SAVED_STATE_REGISTRY_OWNER_KEY] = viewModelStoreOwner as SavedStateRegistryOwner
            }
        }.getOrNull()
    }
}