package com.zest.autouikit.core.models

import kotlinx.serialization.Serializable

@Serializable
data class PreviewComposable(
    val className: String,
    val componentName: String,
    val group: String
)