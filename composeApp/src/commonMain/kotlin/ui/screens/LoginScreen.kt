package ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.koin.compose.viewModel.koinViewModel
import ui.viewModels.LoginViewModel

@Composable
fun LoginScreen() {
    val viewModel = koinViewModel(LoginViewModel::class)
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.fillMaxSize().scrollable(scrollState, orientation = Orientation.Vertical),
        contentAlignment = Alignment.Center
    ) {
        Card {
            Column(
                modifier = Modifier.padding(horizontal = 80.dp, vertical = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login to PowerDNS manager", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    modifier = Modifier.width(340.dp),
                    value = viewModel.uid.value,
                    onValueChange = { viewModel.setUid(it) },
                    label = { Text("Username Or Email") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.width(340.dp),
                    value = viewModel.password.value,
                    onValueChange = { viewModel.setPassword(it) },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ElevatedButton(onClick = { /*TODO*/ }) {
                    Text("Login ${viewModel.uid.value} ${viewModel.password.value}")
                }
            }
        }
    }
}
