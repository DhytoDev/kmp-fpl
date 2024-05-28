package dev.dhyto.fpl.presentation.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dhyto.fpl.domain.entities.Player
import dev.dhyto.fpl.presentation.team.teamPicks.TeamPickListView
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MyTeamScreen(
    navigator: Navigator,
    myTeamViewModel: MyTeamViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabItems = listOf("Points", "Pick Team", "Transfers")

    val pagerState = rememberPagerState { tabItems.size }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        myTeamViewModel.handleEvent(MyTeamEvent.GetMyTeam)
    }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(8.dp),
            ) {
                MyTeamTabBar(
                    tabItems = tabItems,
                    selectedTabIndex = selectedTabIndex,
                    onClick = {
                        selectedTabIndex = it

                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                    }
                )
            }
        }) {
        LaunchedEffect(
            pagerState.currentPage,
            pagerState.isScrollInProgress
        ) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pos ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (pos) {
                    0 -> Box {}
                    1 -> {
                        val selectedPlayerProfile = remember { mutableStateOf<Player?>(null) }

                        TeamPickListView(
                            state = myTeamViewModel.state.collectAsStateWithLifecycle().value,
                            eventHandler = myTeamViewModel::handleEvent,
                            navigator = navigator,
                            gameWeek = myTeamViewModel.currentGameWeek.collectAsState().value,
                            selectedPlayer = selectedPlayerProfile.value,
                            selectPlayer = { selectedPlayerProfile.value = it },
                            closeBottomSheet = { selectedPlayerProfile.value = null },
                        )
                    }

                    2 -> Box {}
                }
            }
        }
    }
}