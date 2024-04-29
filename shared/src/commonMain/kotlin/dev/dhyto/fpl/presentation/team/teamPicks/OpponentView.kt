package dev.dhyto.fpl.presentation.team.teamPicks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OpponentView(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    textColor: Color = Color.Black,
    shortName: String = "BLANK",
    lineHeight: TextUnit = TextUnit.Unspecified,
    fontSize: TextUnit = 8.sp,
) {
    Box(
        modifier = modifier.background(color).basicMarquee()
    ) {
        Text(
            shortName,
            modifier = Modifier
                .align(Alignment.Center).padding(2.dp),
            fontSize = fontSize,
            color = textColor,
            lineHeight = lineHeight,
        )
    }
}