package com.example.otttv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class Screen {
    Login,
    Register,
    Home
}

data class ContentItem(
    val title: String,
    val type: String,
    val access: String
)

@Composable
fun OttTvApp() {
    var screen by remember { mutableStateOf(Screen.Login) }
    var userName by remember { mutableStateOf("") }
    var membership by remember { mutableStateOf("GRATIS") }

    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF0A0F1C), Color(0xFF111A2F))
                    )
                )
        ) { innerPadding ->
            when (screen) {
                Screen.Login -> LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLogin = {
                        userName = it
                        screen = Screen.Home
                    },
                    onGoRegister = { screen = Screen.Register }
                )

                Screen.Register -> RegisterScreen(
                    modifier = Modifier.padding(innerPadding),
                    onComplete = { name, selectedMembership ->
                        userName = name
                        membership = selectedMembership
                        screen = Screen.Home
                    },
                    onGoLogin = { screen = Screen.Login }
                )

                Screen.Home -> HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    userName = userName,
                    membership = membership
                )
            }
        }
    }
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (String) -> Unit,
    onGoRegister: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("OTT TV", style = MaterialTheme.typography.displaySmall, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Inicio de sesión (demo)", color = Color(0xFFB5C7FF))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onLogin("Invitado") }) {
            Text("Entrar con usuario/contraseña")
        }
        TextButton(onClick = onGoRegister) {
            Text("Crear cuenta")
        }
    }
}

@Composable
private fun RegisterScreen(
    modifier: Modifier = Modifier,
    onComplete: (String, String) -> Unit,
    onGoLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Elige tu membresía", color = Color(0xFFB5C7FF))
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { onComplete("Nuevo usuario", "GRATIS") }) {
                Text("Gratis")
            }
            Button(onClick = { onComplete("Nuevo usuario", "PREMIUM · 5€") }) {
                Text("Premium 5€")
            }
        }
        TextButton(onClick = onGoLogin) {
            Text("Volver a login")
        }
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    userName: String,
    membership: String
) {
    val featured = listOf(
        ContentItem("Canal 24/7 Noticias", "24/7", "Incluido"),
        ContentItem("Evento en vivo", "LIVE", "Premium"),
        ContentItem("Película estreno", "PPV", "Pagar por ver"),
        ContentItem("Vimeo Creator Pick", "VIMEO", "Incluido")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Hola, $userName",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Membresía actual: $membership",
            color = Color(0xFFB5C7FF)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("Catálogo principal", color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(featured) { item ->
                ContentCard(item)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Siguientes pasos: conecta Firebase Auth, Stripe/Google Play Billing, Vimeo API y backend OTT.",
            color = Color(0xFFB5C7FF)
        )
    }
}

@Composable
private fun ContentCard(item: ContentItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.45f)
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2440))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.type, color = Color(0xFF90A7FF), fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(item.title, color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Text(item.access, color = Color(0xFFD8E2FF))
        }
    }
}
