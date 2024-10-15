package com.jay.stockeme.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jay.stockeme.Constant
import com.jay.stockeme.UseCases.validateEmail
import com.jay.stockeme.UseCases.validatePassword
import com.jay.stockeme.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignupScreen(context: Context, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    // Validation states
    var passwordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val viewModel: AuthViewModel = koinViewModel()

    // Observe the sign-up status from the ViewModel
    LaunchedEffect(Unit) {
        viewModel.signUpStatus.collectLatest { signUpStatus ->
            signUpStatus?.let {
                // Handle success or failure
                if (it == Constant.SIGN_UP_SUCCESS) {
                    Toast.makeText(context, "Sign-up Successful!", Toast.LENGTH_LONG).show()
                    navController.popBackStack() // Navigate to the home screen on success
                } else {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show() // Display the error message
                    Log.e("SignUp", "Sign-up failed: $it") // Log the failure for debugging
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(8.dp))

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (it.isNotEmpty()) null else validateEmail(it)
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = {
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField with Visibility Toggle
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = if (it.isNotEmpty()) null else validatePassword(it)
            },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Button
        Button(
            onClick = {
                emailError = validateEmail(email)
                passwordError = validatePassword(password)
                if (emailError == null && passwordError == null) {
                    // Call sign-up method from ViewModel
                    viewModel.signUpWithEmailAndPassword(email = email, password = password)
                }
            },
            enabled = emailError == null && passwordError == null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navigate to Login Button
        TextButton(modifier = Modifier.fillMaxWidth(), onClick = { navController.popBackStack() }) {
            Text("Login")
        }
    }
}

