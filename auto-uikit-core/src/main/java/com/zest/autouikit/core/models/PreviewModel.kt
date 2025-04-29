package com.zest.autouikit.core.models

import kotlinx.serialization.Serializable

@Serializable
data class PreviewModel(val previewGroups: List<PreviewGroup>, val colors: List<PreviewColor>)
