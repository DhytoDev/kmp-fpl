@file:OptIn(ExperimentalLayoutApi::class)

package dev.dhyto.fpl.presentation.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.domain.base.Failure
import dev.dhyto.fpl.domain.entities.ManagerEntry
import dev.dhyto.fpl.presentation.UiState
import dev.dhyto.fpl.presentation.navigation.NavigationRoute
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleObserver
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MyTeamScreen(
    navigator: Navigator,
//    myTeamViewModel: MyTeamViewModel,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabItems = listOf("Points", "Pick Team", "Transfers")

    val pagerState = rememberPagerState { tabItems.size }

    val coroutineScope = rememberCoroutineScope()

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
                        val myTeamViewModel = koinViewModel(vmClass = MyTeamViewModel::class)

                        val lifecycleOwner = LocalLifecycleOwner.current

                        DisposableEffect(lifecycleOwner) {
                            val observer = object : LifecycleObserver {
                                override fun onStateChanged(state: Lifecycle.State) {
                                    when (state) {
                                        Lifecycle.State.Active -> {
                                            myTeamViewModel.handleEvent(MyTeamEvent.GetMyTeam(570461))
                                        }

                                        Lifecycle.State.InActive -> {
                                            Logger.d("inactive")

                                        }

                                        Lifecycle.State.Destroyed -> {
                                            Logger.d("destroyed")

                                        }

                                        Lifecycle.State.Initialized -> {
                                            Logger.d("initialized")
                                        }

                                        else -> {}
                                    }
                                }
                            }
                            lifecycleOwner.lifecycle.addObserver(observer)
                            onDispose {
                                lifecycleOwner.lifecycle.removeObserver(observer)
                            }
                        }

                        PickTeam(
                            state = myTeamViewModel.state.collectAsState().value,
                            eventHandler = myTeamViewModel::handleEvent,
                            navigator = navigator
                        )
                    }

                    2 -> Box {}
                }
            }
        }
    }
}

@Composable
fun PickTeam(
    state: UiState<List<ManagerEntry>>,
    eventHandler: (event: MyTeamEvent) -> Unit,
    navigator: Navigator,
) {
    when (state) {
        is UiState.ErrorState -> {
            if (state.failure is Failure.UnauthenticatedFailure) {
                UnauthenticatedLayout(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navigator.navigate(
                            NavigationRoute.SignInRoute.route,
                            NavOptions(launchSingleTop = true)
                        )
                    }
                )
            }
        }

        UiState.InitialState -> {}
        UiState.LoadingState -> {}
        is UiState.SuccessState<List<ManagerEntry>> -> {
            LazyColumn {
                item {
                    val gk = state.data.filter { it.player.getPosition() == "GKP" }
                    PlayersLine(gk)
                }
                item {
                    val def = state.data.filter { it.player.getPosition() == "DEF" }
                    PlayersLine(def)
                }
                item {
                    val mid = state.data.filter { it.player.getPosition() == "MID" }
                    PlayersLine(mid)
                }
                item {
                    val fwd = state.data.filter { it.player.getPosition() == "FWD" }
                    PlayersLine(fwd)
                }
            }
        }
    }
}

@Composable
private fun PlayersLine(players: List<ManagerEntry>) {
    LazyRow {
        itemsIndexed(players) { _, player ->
            PlayerView(
                photoUrl = player.player.photoUrl,
                playerName = player.player.displayName,
            )
        }
    }
}

@Composable
fun PlayerView(
    photoUrl: String,
    playerName: String,
) {
    val painterResource = asyncPainterResource(photoUrl)

    Column {
        KamelImage(
            modifier = Modifier.size(50.dp),
            resource = painterResource,
            contentDescription = null
        )
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        ) {
            Text(playerName)
        }
    }
}

@Composable
internal fun UnauthenticatedLayout(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Text("Sign In Required")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onClick) {
            Text("Sign In Now")
        }
    }
}