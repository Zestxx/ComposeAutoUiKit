package com.zest.autouikit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zest.autouikit.core.models.PreviewGroup

@Composable
internal fun GroupsScreen(
    groups: List<PreviewGroup>,
    onSelect: (PreviewGroup) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Component groups",
            style = MaterialTheme.typography.h4,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            groups.forEach { group ->
                item {
                    GroupItem(onSelect, group)
                }
            }
        }
    }
}

@Composable
private fun GroupItem(
    onSelect: (PreviewGroup) -> Unit,
    group: PreviewGroup
) {
    Card(
        modifier = Modifier
            .clickable { onSelect(group) }
            .background(color = Color.White),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.White)
        ) {
            Text(
                group.name,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}