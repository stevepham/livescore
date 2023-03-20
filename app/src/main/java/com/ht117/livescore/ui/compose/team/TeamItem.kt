package com.ht117.livescore.ui.compose.team

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ht117.data.model.Team

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamItem(team: Team,
             modifier: Modifier = Modifier,
             onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        elevation = 4.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = team.logo,
                contentDescription = "",
                modifier = modifier
                    .width(90.dp)
                    .fillMaxHeight()
                    .background(Color.Blue)
            )
            Text(
                modifier = modifier
                    .padding(start = 8.dp),
                text = team.name,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
