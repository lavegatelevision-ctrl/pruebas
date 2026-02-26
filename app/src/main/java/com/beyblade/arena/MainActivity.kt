package com.beyblade.arena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.beyblade.arena.ui.BeybladeApp
import com.beyblade.arena.ui.BeybladeTab
import com.beyblade.arena.ui.theme.BeybladeArenaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeybladeArenaTheme {
                BeybladeRoot()
            }
        }
    }
}

@Composable
private fun BeybladeRoot() {
    var currentTab by rememberSaveable { mutableStateOf(BeybladeTab.Home) }
    BeybladeApp(currentTab = currentTab, onTabSelected = { currentTab = it })
}
