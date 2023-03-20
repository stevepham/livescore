package com.ht117.livescore.ui.compose.match

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ht117.data.model.Match
import com.ht117.livescore.ext.formatDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchItem(match: Match, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = match.home, modifier = modifier
                    .weight(1F)
                    .width(110.dp)
                    .wrapContentHeight()
                    .padding(start = 8.dp)
            )

            Text(
                text = match.date.formatDate(),
                textAlign = TextAlign.Center,
                modifier = modifier
                    .weight(1F)
                    .wrapContentHeight()
            )

            Text(
                text = match.away,
                modifier = modifier
                    .weight(1F)
                    .width(110.dp)
                    .wrapContentHeight()
                    .padding(end = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
