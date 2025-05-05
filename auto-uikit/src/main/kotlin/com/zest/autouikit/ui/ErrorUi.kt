package com.zest.autouikit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zest.autouikit.core.models.PreviewComponent

@Composable
fun SerializationErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            """
            Preview loading error
            Perhaps code shrinking is enabled for this build type.
            """.trimIndent(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Red
        )
    }
}

@Composable
fun ComponentPreviewError(component: PreviewComponent, message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(color = Color.White),
    ) {
        val componentName = component.componentName.ifEmpty { component.functionName }
        Text(
            text = "$componentName rendering error -> $message",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Red
        )
    }
}