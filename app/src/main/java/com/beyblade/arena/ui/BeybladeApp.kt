package com.beyblade.arena.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class BeybladeTab(val label: String, val icon: ImageVector) {
    Home("Inicio", Icons.Rounded.Home),
    Catalog("Beys", Icons.Rounded.AutoAwesome),
    Battles("Batallas", Icons.Rounded.EmojiEvents),
    Profile("Perfil", Icons.Rounded.Person)
}

data class BeybladeInfo(
    val name: String,
    val type: String,
    val attack: Int,
    val defense: Int,
    val stamina: Int
)

private val featuredBeys = listOf(
    BeybladeInfo("Dran Sword", "Ataque", 95, 70, 60),
    BeybladeInfo("Hells Scythe", "Balance", 82, 85, 80),
    BeybladeInfo("Wizard Arrow", "Resistencia", 68, 75, 98)
)

@Composable
fun BeybladeApp(currentTab: BeybladeTab, onTabSelected: (BeybladeTab) -> Unit) {
    val myBeys = remember { mutableStateListOf<BeybladeInfo>() }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BeybladeTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = tab == currentTab,
                        onClick = { onTabSelected(tab) },
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.label) },
                        label = { Text(text = tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentTab) {
            BeybladeTab.Home -> HomeScreen(innerPadding)
            BeybladeTab.Catalog -> CatalogScreen(innerPadding = innerPadding, myBeys = myBeys, onAddBey = { myBeys.add(it) })
            BeybladeTab.Battles -> BattlesScreen(innerPadding)
            BeybladeTab.Profile -> ProfileScreen(innerPadding)
        }
    }
}

@Composable
private fun HomeScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(Modifier.height(8.dp))
            Text("Beyblade Arena X", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(
                "Tu centro para crear combos, seguir torneos y analizar estadísticas.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            HeroCard()
        }

        item {
            Text("Top Beys de la semana", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        }

        items(featuredBeys) { bey ->
            BeybladeCard(bey)
        }
    }
}

@Composable
private fun HeroCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.FlashOn, contentDescription = null, tint = Color.White)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Torneo Online", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    "Inscripciones abiertas para la Beyblade World Cup Latina.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun BeybladeCard(bey: BeybladeInfo) {
    Card(shape = RoundedCornerShape(18.dp), elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(bey.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Tipo: ${bey.type}", color = MaterialTheme.colorScheme.primary)
            StatLine("Ataque", bey.attack)
            StatLine("Defensa", bey.defense)
            StatLine("Resistencia", bey.stamina)
        }
    }
}

@Composable
private fun StatLine(name: String, value: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(name, modifier = Modifier.weight(1f))
        Text("$value", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CatalogScreen(
    innerPadding: PaddingValues,
    myBeys: List<BeybladeInfo>,
    onAddBey: (BeybladeInfo) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var attack by remember { mutableStateOf("") }
    var defense by remember { mutableStateOf("") }
    var stamina by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Rounded.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text("Tus Beyblades", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            Text(
                "Crea y guarda tus propios Beyblades para probar combos.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(shape = RoundedCornerShape(16.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Agregar Beyblade", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = attack, onValueChange = { attack = it }, label = { Text("Ataque") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = defense, onValueChange = { defense = it }, label = { Text("Defensa") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = stamina, onValueChange = { stamina = it }, label = { Text("Resistencia") }, modifier = Modifier.fillMaxWidth())

                    Button(
                        onClick = {
                            if (name.isNotBlank() && type.isNotBlank()) {
                                onAddBey(
                                    BeybladeInfo(
                                        name = name.trim(),
                                        type = type.trim(),
                                        attack = attack.toIntOrNull() ?: 0,
                                        defense = defense.toIntOrNull() ?: 0,
                                        stamina = stamina.toIntOrNull() ?: 0
                                    )
                                )
                                name = ""
                                type = ""
                                attack = ""
                                defense = ""
                                stamina = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Beyblade")
                    }
                }
            }
        }

        item {
            Text("Mis Beyblades", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        }

        if (myBeys.isEmpty()) {
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Text(
                        "Aún no agregas Beyblades. Crea el primero arriba.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(myBeys) { bey ->
                BeybladeCard(bey)
            }
        }
    }
}

@Composable
private fun BattlesScreen(innerPadding: PaddingValues) {
    GenericListScreen(
        title = "Batallas recientes",
        subtitle = "Revisa resultados, duración y KOs espectaculares.",
        items = listOf(
            "Dran Sword vs Wizard Arrow · 3-2",
            "Hells Scythe vs Phoenix Wing · 1-3",
            "Shark Edge vs Knight Shield · 3-0",
            "Leon Claw vs Viper Tail · 2-3"
        ),
        icon = Icons.Rounded.Groups,
        innerPadding = innerPadding
    )
}

@Composable
private fun ProfileScreen(innerPadding: PaddingValues) {
    GenericListScreen(
        title = "Perfil Blader",
        subtitle = "Sigue tu ranking, logros y evolución de combos.",
        items = listOf(
            "Liga actual: Diamante II",
            "Victorias: 148",
            "Racha máxima: 12",
            "Logro desbloqueado: Rey del Estadio"
        ),
        icon = Icons.Rounded.EmojiEvents,
        innerPadding = innerPadding
    )
}

@Composable
private fun GenericListScreen(
    title: String,
    subtitle: String,
    items: List<String>,
    icon: ImageVector,
    innerPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(items) { line ->
            Card(shape = RoundedCornerShape(16.dp)) {
                Text(
                    text = line,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
