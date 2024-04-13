package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import pdnsmanager.commonCompose.resources.Res
import pdnsmanager.commonCompose.resources.dont_have_an_account_create_one
import pdnsmanager.commonCompose.resources.icon_warning
import pdnsmanager.commonCompose.resources.login
import pdnsmanager.commonCompose.resources.login_to_powerdns_manager
import pdnsmanager.commonCompose.resources.password
import pdnsmanager.commonCompose.resources.username_or_email
import ui.components.PMCButton
import ui.components.PMCOutlinedTextField
import ui.components.PMCPasswordTextField
import ui.components.PMCTextButton
import ui.viewModels.LoginViewModel

@Composable
fun LoginScreen(
	navigateToRegister: () -> Unit,
) {
	val viewModel = koinViewModel(LoginViewModel::class)
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
						stringResource(Res.string.login_to_powerdns_manager),
						style = MaterialTheme.typography.headlineLarge
					)
					Spacer(modifier = Modifier.height(40.dp))

					// box to show error
					if (uiState.isError) {
						Row(
							modifier = Modifier.padding(8.dp),
							horizontalArrangement = Arrangement.Center,
							verticalAlignment = Alignment.CenterVertically
						) {
							Icon(
								Icons.Filled.Warning,
								contentDescription = stringResource(Res.string.icon_warning),
								tint = MaterialTheme.colorScheme.error
							)
							Spacer(modifier = Modifier.width(5.dp))
							Text(
								text = uiState.error ?: "",
								style = MaterialTheme.typography.bodyMedium,
								color = MaterialTheme.colorScheme.error
							)
						}
					}

					PMCOutlinedTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.uid,
						onValueChange = { viewModel.setUid(it) },
						label = { Text(stringResource(Res.string.username_or_email)) },
						isError = uiState.isError
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCPasswordTextField(
						modifier = Modifier.width(340.dp),
						value = uiState.password,
						onValueChange = { viewModel.setPassword(it) },
						label = { Text(stringResource(Res.string.password)) },
						isError = uiState.isError
					)
					Spacer(modifier = Modifier.height(10.dp))
					PMCButton(
						onClick = { viewModel.login() },
						enabled = !uiState.isLoading,
						loading = uiState.isLoading
					) {
						Text(stringResource(Res.string.login), style = MaterialTheme.typography.bodyLarge)
					}
				}

				Spacer(modifier = Modifier.height(15.dp))
				PMCTextButton(
					text = stringResource(Res.string.dont_have_an_account_create_one),
					onClick = navigateToRegister,
				)
			}
		}
	}
}
