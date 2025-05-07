package com.zest.autouikit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zest.autouikit.core.models.PreviewModel
import com.zest.autouikit.ui.AutoUiKitActivity
import com.zest.autouikit.ui.PreviewNavHost
import com.zest.autouikit.ui.SerializationErrorScreen
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object AutoUiKit {
    internal var previewModel: PreviewModel? = null
    private var isModelSerialized: Boolean? = null

    fun init(context: Context) {
        val jsonModel = context.applicationContext
            .classLoader
            .getResourceAsStream("com/zest/autouikit/GeneratedPreviewModel.json")
            .use { it.bufferedReader().readText() }

        try {
            previewModel = Json.decodeFromString<PreviewModel>(jsonModel)
            isModelSerialized = true
        } catch (e: SerializationException) {
            e.printStackTrace()
            isModelSerialized = false
        }
    }

    @Composable
    fun PreviewScreen(modifier: Modifier = Modifier) {
        if (isModelSerialized == true) {
            PreviewNavHost(modifier)
        } else {
            SerializationErrorScreen()
        }
    }

    fun startPreviewActivity(context: Context) {
        context.startActivity(AutoUiKitActivity.getInstance(context))
    }
}