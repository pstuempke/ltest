package com.stuempke.luzuatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stuempke.luzuatest.navigation.MainNavigation
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




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LuziaTestTheme {
    }
}