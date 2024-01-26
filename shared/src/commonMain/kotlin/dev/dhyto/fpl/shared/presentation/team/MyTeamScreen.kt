package dev.dhyto.fpl.shared.presentation.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyTeamScreen(navigator: Navigator) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabItems = listOf("Points", "Pick Team", "Transfers")

    val pagerState = rememberPagerState {
        tabItems.size
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(8.dp)
            ) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.padding(12.dp),
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index

                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedTabIndex)
                            }
                        }
                    ) {
                        Text(title)
                    }
                }
            }
        }
    ) {
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tabItems[index],
                    fontSize = 18.sp
                )
            }
        }
    }
}