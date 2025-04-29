package com.zest.uikit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zest.autouikit.UiPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UiPreview.init(this)
        setContent {
            UiPreview.PreviewScreen()
        }
    }
}
