@file:OptIn(ExperimentalMaterial3Api::class)

package com.stuempke.luzuatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.stuempke.presentation.navigation.MainNavigation
import com.stuempke.luzuatest.ui.theme.LuziaTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuziaTestTheme {
                MainNavigation()
            }
        }
    }
}