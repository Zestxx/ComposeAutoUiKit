package com.zest.uikit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zest.autouikit.AutoUiKit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AutoUiKit.init(this)
        setContent {
            AutoUiKit.PreviewScreen()
        }
    }
}
