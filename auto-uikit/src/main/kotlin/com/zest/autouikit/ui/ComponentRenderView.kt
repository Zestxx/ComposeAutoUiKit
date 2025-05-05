package com.zest.autouikit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.reflect.getDeclaredComposableMethod
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import auto.uikit.compose.R
import com.zest.autouikit.core.models.PreviewComponent
import com.zest.autouikit.reflection.CommonPreviewUtils
import com.zest.autouikit.reflection.asPreviewProviderClass
import com.zest.autouikit.reflection.getComposableMethod
import java.lang.Class
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Composable
internal fun ComponentRenderView(component: PreviewComponent, composer: Composer) {
    val composableClass = Class.forName(component.className)
    if (component.previewProvider.isNullOrEmpty()) {
        val composableMethod = composableClass.getDeclaredComposableMethod(
            component.functionName,
        )
        CommonPreviewUtils.invokeComposableViaReflection(
            composableMethod,
            composer,
        )
    } else {
        val previewProviderClass = component.previewProvider?.asPreviewProviderClass()
        val returnType = previewProviderClass?.methods
            ?.find { it.name == "getValues" }
            ?.genericReturnType as ParameterizedType
        val previewValueType: Class<*>? = returnType.actualTypeArguments.firstOrNull()
            ?.let { type: Type ->
                if (type is ParameterizedType) {
                    type.rawType
                } else {
                    type
                }
            } as? Class<*>


        val previewArgs = CommonPreviewUtils.getPreviewProviderParameters(previewProviderClass, -1)
        val isListType = List::class.java.isAssignableFrom(previewArgs.first()?.javaClass)
        val xxx = previewArgs.first()?.let { it::class }
//        val previewParameterType = if (isListType) {
//            previewArgs.first()?.javaClass
//        } else {
//            previewArgs.first()?.javaClass
//        }
        if (previewValueType == null) {
            ComponentPreviewError(component, "Preview parameter type error")
            return
        }
        val composableMethod = composableClass.getComposableMethod(
            component,
            previewValueType
        )
        if (composableMethod != null) {
            Column {
                previewArgs.forEach { previewData ->
                    ExpandableText(previewData.toString())
                    Spacer(Modifier.height(8.dp))
                    CommonPreviewUtils.invokeComposableViaReflection(
                        composableMethod,
                        composer,
                        previewData
                    )

                    Spacer(Modifier.height(16.dp))
                }
            }
        } else {
            ComponentPreviewError(
                component = component,
                message = "No composable preview function with ${previewValueType.toString()} argument type"
            )
        }
    }
}

inline fun sds() {}

@Composable
private fun ExpandableText(previewData: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotateAnimation by animateFloatAsState(
        targetValue = if (expanded) 180F else 0F
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Preview data",
                modifier = Modifier
                    .padding(vertical = 4.dp),
                style = MaterialTheme.typography.button,
                color = Color.Blue,
            )
            Icon(
                painterResource(R.drawable.ic_arrow_down_24),
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier
                    .graphicsLayer {
                        rotationX = arrowRotateAnimation
                    }
            )
        }
        AnimatedVisibility(visible = expanded) {
            Text(
                text = previewData,
                modifier = Modifier
                    .padding(vertical = 4.dp),
                style = MaterialTheme.typography.caption
            )
        }
    }
}