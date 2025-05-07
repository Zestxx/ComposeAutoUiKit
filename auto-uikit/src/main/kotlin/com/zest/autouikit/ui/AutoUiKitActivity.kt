package com.zest.autouikit.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zest.autouikit.AutoUiKit

internal class AutoUiKitActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoUiKit.PreviewScreen()
        }
    }

    internal companion object {
        fun getInstance(context: Context) = Intent(context, AutoUiKitActivity::class.java)
    }
}