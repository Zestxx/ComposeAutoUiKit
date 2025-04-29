package com.zest.autouikit.reflection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import com.zest.autouikit.core.models.PreviewComposable

@Composable
internal fun ComposeReflection(component: PreviewComposable, composer: Composer) {
    CommonPreviewUtils.invokeComposableViaReflection(
        component.className,
        component.componentName,
        composer
    )
}
