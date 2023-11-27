package dev.dhyto.fpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.dhyto.fpl.shared.presentation.App

class MainActivity : ComponentActivity() {
//    private val dreamTeamViewModel: DreamTeamViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App(d)
//}