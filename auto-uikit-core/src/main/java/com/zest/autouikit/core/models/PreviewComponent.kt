package com.zest.autouikit.core.models

import kotlinx.serialization.Serializable

@Serializable
data class PreviewComponent(
    val className: String,
    val functionName: String,
    val group: String,
    val componentName: String = "",
    val previewProvider: String? = null,
)