package com.zest.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zest.autouikit.core.annotation.DesignComponent

object TestObject {
    @DesignComponent(group = "Test object")
    @Composable
    private fun TextUiComponentPreview() {
        Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
            Text(modifier = Modifier.align(Alignment.Center), text = "Test sastext")
        }
    }


    @DesignComponent(group = "Test object")
    @Composable
    private fun ButtonUiComponentPreview() {
        Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
            Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
                Text(text = "Button")
            }
        }
    }

}

class TestClass {
    @DesignComponent(group = "Test class")
    @Composable
    private fun TextUiComponentPreview() {
        Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
            Text(modifier = Modifier.align(Alignment.Center), text = "Test sastext")
        }
    }


    @DesignComponent(group = "Test class")
    @Composable
    private fun ButtonUiComponentPreview() {
        Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
            Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
                Text(text = "Button")
            }
        }
    }

}

@DesignComponent(group = "Test fun")
@Composable
fun TextUiComponent() {
    Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Test text")
    }
}


@DesignComponent(group = "Test fun")
@Composable
fun ButtonUiComponent() {
    Box(modifier = Modifier.fillMaxWidth().height(35.dp)) {
        Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
            Text(text = "Button")
        }
    }
}

