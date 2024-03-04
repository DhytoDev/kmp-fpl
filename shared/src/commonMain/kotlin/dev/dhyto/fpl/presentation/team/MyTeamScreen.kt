package dev.dhyto.fpl.shared.presentation.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import dev.dhyto.fpl.shared.domain.base.Failure
import dev.dhyto.fpl.shared.presentation.UiState
import dev.dhyto.fpl.shared.presentation.navigation.NavigationRoute
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleObserver
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MyTeamScreen(
    navigator: Navigator,
    myTeamViewModel: MyTeamViewModel,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabItems = listOf("Points", "Pick Team", "Transfers")

    val pagerState = rememberPagerState { tabItems.size }

    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = object : LifecycleObserver {
            override fun onStateChanged(state: Lifecycle.State) {
                when (state) {
                    Lifecycle.State.Active -> {
                        Logger.d("active")
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
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
                myTeamViewModel.getMyTeam()
            }
        }

        val uiState by myTeamViewModel.state.collectAsStateWithLifecycle()

        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is UiState.ErrorState -> {
                        if ((uiState as UiState.ErrorState).failure is Failure.UnauthenticatedFailure) {
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
                    else -> {
                        Text(
                            text = tabItems[index],
                            fontSize = 18.sp
                        )
                    }
                }
            }
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