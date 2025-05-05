package com.zest.autouikit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zest.autouikit.AutoUiKit
import com.zest.autouikit.core.models.PreviewComponent

@Composable
internal fun ComponentsScreen(groupName: String) {
    val group = remember {
        AutoUiKit.previewModel?.previewGroups?.find { it.name == groupName }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier
                .fillMaxSize()
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
private fun ComponentItem(component: PreviewComponent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Workaround for Scaffold component
            .sizeIn(maxHeight = Dp(LocalWindowInfo.current.containerSize.height.toFloat())),
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
                text = component.componentName.ifEmpty { component.functionName },
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            ComponentRenderView(component, currentComposer)
        }
    }
}

