package com.stuempke.luzuatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
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

@Composable
private fun MainNavigation() {
    val navController = rememberNavController()


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LuziaTestTheme {
    }
}