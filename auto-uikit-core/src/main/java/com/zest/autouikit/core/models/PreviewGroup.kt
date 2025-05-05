package com.zest.autouikit.core.models

import kotlinx.serialization.Serializable

@Serializable
data class PreviewGroup(val name: String, val previewComponents: List<PreviewComponent>)