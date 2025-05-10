package uk.kulikov.flippercorp2025

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import uk.kulikov.flippercorp2025.root.DefaultRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            settings = SharedPreferencesSettings(
                getSharedPreferences(
                    "settings.xml",
                    MODE_PRIVATE
                )
            ),
        )

        setContent {
            App(root)
        }
    }
}