package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewModel.koinViewModel
import pdnsmanager.launchpad.generated.resources.Res
import pdnsmanager.launchpad.generated.resources.already_have_an_account_login
import pdnsmanager.launchpad.generated.resources.create_an_account_in_powerdns_manager
import pdnsmanager.launchpad.generated.resources.email
import pdnsmanager.launchpad.generated.resources.full_name
import pdnsmanager.launchpad.generated.resources.password
import pdnsmanager.launchpad.generated.resources.signup
import pdnsmanager.launchpad.generated.resources.username
import ui.components.PMCButton
import ui.components.PMCOutlinedTextField
import ui.components.PMCPasswordTextField
import ui.components.PMCTextButton
import ui.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(
	navigateToLogin: () -> Unit
) {
	val viewModel = koinViewModel(RegisterViewModel::class)
	val uiState by viewModel.uiState.collectAsState()
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Card {
			Column(
				modifier = Modifier.padding(horizontal = 80.dp, vertical = 60.dp),
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text(
						stringResource(Res.string.create_an_account_in_powerdns_manager),
						style = MaterialTheme.typography.headlineLarge
					)
					Spacer(modifier = Modifier.height(40.dp))

					PMCOutlinedTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.name,
						onValueChange = { viewModel.setName(it) },
						label = { Text(stringResource(Res.string.full_name)) },
						error = uiState.nameErrors?.get(0)
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCOutlinedTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.username,
						onValueChange = { viewModel.setUsername(it) },
						label = { Text(stringResource(Res.string.username)) },
						error = uiState.usernameErrors?.get(0)
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCOutlinedTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.email,
						onValueChange = { viewModel.setEmail(it) },
						label = { Text(stringResource(Res.string.email)) },
						error = uiState.emailErrors?.get(0)
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCPasswordTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.password,
						onValueChange = { viewModel.setPassword(it) },
						label = { Text(stringResource(Res.string.password)) },
						error = uiState.passwordErrors?.get(0)
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCButton(
						onClick = { viewModel.register() },
						enabled = !uiState.isLoading,
						loading = uiState.isLoading
					) {
						Text(stringResource(Res.string.signup), style = MaterialTheme.typography.bodyLarge)
					}
				}

				Spacer(modifier = Modifier.height(15.dp))
				PMCTextButton(
					onClick = navigateToLogin,
				) {
					Text(stringResource(Res.string.already_have_an_account_login))
				}
			}
		}
	}
}
