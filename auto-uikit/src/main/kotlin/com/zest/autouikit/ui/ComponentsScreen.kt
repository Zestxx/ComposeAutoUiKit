package com.zest.autouikit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zest.autouikit.UiPreview
import com.zest.autouikit.core.models.PreviewComposable
import com.zest.autouikit.reflection.ComposeReflection

@Composable
internal fun ComponentsScreen(groupName: String) {
    val group = remember {
        UiPreview.previewModel?.previewGroups?.find { it.name == groupName }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = groupName,
            style = MaterialTheme.typography.h4,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            group?.previewComponents?.forEach { component ->
                item { ComponentItem(component) }
            }
        }
    }
}

@Composable
private fun ComponentItem(component: PreviewComposable) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Workaround for Scaffold component
            .sizeIn(maxHeight = Dp(LocalConfiguration.current.screenHeightDp.toFloat())),
        elevation = 4.dp,
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(color = Color.White)
        ) {
            Text(
                component.componentName,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            ComposeReflection(component, currentComposer)
        }
    }
}

