package com.zest.autouikit.processor.generator

import com.zest.autouikit.processor.annotation.findAnnotation
import com.zest.autouikit.processor.annotation.getArgumentValue
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.zest.autouikit.core.annotation.DesignComponent
import com.zest.autouikit.processor.utils.FunctionUtils
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ksp.writeTo

@Deprecated("Legacy class. Should be removed.")
internal class ComponentListGeneratorWithReflection(private val codeGenerator: CodeGenerator) {

    fun generate(listedFunctions: Sequence<KSFunctionDeclaration>) {

        val fileSpec = FileSpec.builder(
            packageName = "com.redmadrobot.preview", fileName = "UiKitComponentList"
        )
            .addImport("androidx.compose.foundation.layout", "Column", "Spacer", "padding", "height", "fillMaxSize")
            .addImport("androidx.compose.foundation.lazy", "LazyColumn")
            .addImport("androidx.compose.material", "Divider", "Text")
            .addImport("androidx.compose.ui", "Modifier", "unit.dp")
            .addImport("com.redmadrobot.preview.core.reflection", "CommonPreviewUtils")
            .addImport("androidx.compose.runtime", "currentComposer")
            .addFunction(getUiKitComponentListFunction(listedFunctions))
            .build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    private fun getUiKitComponentListFunction(listedFunctions: Sequence<KSFunctionDeclaration>): FunSpec {
        val itemCode: (KSFunctionDeclaration) -> String = { componentFunction ->
            val parent = FunctionUtils.getClass(componentFunction)
            if (parent.isNotEmpty()) {
                val annotation = componentFunction.findAnnotation(DesignComponent::class)
                val group = annotation?.getArgumentValue<String>("group")
                val infoText = if (!group.isNullOrEmpty()) {
                    "name = $componentFunction group = $group"
                } else {
                    "name = $componentFunction"
                }
                """
                    |       item {
                    |           Column {
                    |               Text("$infoText", modifier = Modifier.padding(8.dp))
                    |               Spacer(modifier = Modifier.height(10.dp))
                    |               CommonPreviewUtils.invokeComposableViaReflection(
                    |                   "$parent",
                    |                   "$componentFunction",
                    |                   currentComposer
                    |               )
                    |               Divider()
                    |           }
                    |       }
                    |
                """.trimMargin()
            } else ""
        }

        return FunSpec.builder("UiKitComponentList")
            .addAnnotation(
                AnnotationSpec.builder(ClassName("androidx.compose.runtime", "Composable")).build()
            )
            .addStatement("LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {")
            .apply {
                listedFunctions.forEach {
                    addStatement(itemCode(it))
                }
            }
            .addStatement("}")
            .build()
    }

}

