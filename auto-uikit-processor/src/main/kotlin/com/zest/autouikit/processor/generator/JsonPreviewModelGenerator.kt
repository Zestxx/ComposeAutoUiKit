package com.zest.autouikit.processor.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.zest.autouikit.core.annotation.DesignComponent
import com.zest.autouikit.core.models.PreviewColor
import com.zest.autouikit.core.models.PreviewComposable
import com.zest.autouikit.core.models.PreviewGroup
import com.zest.autouikit.core.models.PreviewModel
import com.zest.autouikit.processor.annotation.findAnnotation
import com.zest.autouikit.processor.annotation.getArgumentValue
import com.zest.autouikit.processor.utils.FunctionUtils
import kotlinx.serialization.json.Json

internal class JsonPreviewModelGenerator(private val codeGenerator: CodeGenerator) {

    fun generate(listedFunctions: Sequence<KSFunctionDeclaration>) {
        val sourceFiles = listedFunctions.mapNotNull { it.containingFile }
        val groups = findGroups(listedFunctions)
        val colors = findColors(listedFunctions)
        val previewModel = PreviewModel(groups, colors)

        codeGenerator.createNewFile(
            dependencies = Dependencies(false, *sourceFiles.toList().toTypedArray()),
            packageName = "com.zest.autouikit",
            fileName = "GeneratedPreviewModel",
            extensionName = "json"
        ).use {
            val jsonModel = Json.encodeToString(value = previewModel)
            it.write(jsonModel.toByteArray())
        }
    }

    private fun findColors(listedFunctions: Sequence<KSFunctionDeclaration>): List<PreviewColor> {
        return emptyList() // TODO Add implementation
    }


    private fun findGroups(listedFunctions: Sequence<KSFunctionDeclaration>) =
        listedFunctions.map { function ->
            val annotation = function.findAnnotation(DesignComponent::class)
            val group = annotation?.getArgumentValue<String>("group").orEmpty()

            PreviewComposable(
                FunctionUtils.getClass(function),
                function.toString(),
                group.ifEmpty { "Undefined Group" }
            )
        }.groupBy { it.group }
            .map {
                PreviewGroup(it.key, it.value)
            }
}

