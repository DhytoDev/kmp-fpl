package dev.dhyto.fpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.dhyto.fpl.presentation.App
import dev.dhyto.fpl.presentation.DreamTeamViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val dreamTeamViewModel: DreamTeamViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dreamTeamViewModel.getDreamTeamSquad(12)

        setContent {
            App(dreamTeamViewModel.theDreamTeams)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App(d)
//}