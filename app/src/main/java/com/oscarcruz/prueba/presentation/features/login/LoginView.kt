package com.oscarcruz.prueba.presentation.features.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun LoginView(
    navigateAcuerdo: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is LoginUiEvent.NavigateToDashboard -> navigateAcuerdo()
                is LoginUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        // Usamos paddingValues en un log o simplemente lo ignoramos,
        // pero el Scaffold REQUIERE que se defina la variable en la lambda.

        Box(modifier = Modifier.fillMaxSize()) {
            LoginContent(
                uiState = uiState,
                onAction = viewModel::onAction
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
@Composable
fun LoginContent(uiState: LoginUiState, onAction: (LoginAction) -> Unit) {
    val focusManager = LocalFocusManager.current
    val (emailFocus, passwordFocus) = remember { FocusRequester.createRefs() }
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .imePadding()){
        val(titulo,inputTextUser,inputTextPassword,buttonLogin) = createRefs()
        Text("Bienvenido",
            modifier = Modifier
                .constrainAs(titulo){
                    top.linkTo(parent.top, margin  = 120.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    Dimension.fillToConstraints
                })

        OutlinedTextField(
            value = uiState.email,
            onValueChange =  { onAction(LoginAction.EmailChanged(it)) },
            label = { Text("User") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
            modifier = Modifier
                .focusRequester(emailFocus)
                .constrainAs(inputTextUser){
                    top.linkTo(titulo.bottom, margin  = 140.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    Dimension.fillToConstraints
                }
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange =  { onAction(LoginAction.PasswordChanged(it)) },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus() // Cierra el teclado
                onAction(LoginAction.Submit) // Dispara el login
            }),
            modifier = Modifier
                .focusRequester(passwordFocus)
                .constrainAs(inputTextPassword){
                    top.linkTo(inputTextUser.bottom, margin  = 60.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    Dimension.fillToConstraints
                }
        )
        Button(
            onClick = {
                onAction(LoginAction.Submit)
            },
            modifier = Modifier
                .width(280.dp)
                .constrainAs(buttonLogin) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(inputTextPassword.bottom, margin = 60.dp)
                },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "login",
                fontSize = 22.sp
            )
        }

    }
}