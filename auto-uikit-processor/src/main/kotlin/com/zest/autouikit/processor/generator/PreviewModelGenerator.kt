package com.zest.autouikit.processor.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSType
import com.zest.autouikit.core.annotation.DesignComponent
import com.zest.autouikit.core.models.PreviewColor
import com.zest.autouikit.core.models.PreviewComponent
import com.zest.autouikit.core.models.PreviewGroup
import com.zest.autouikit.core.models.PreviewModel
import com.zest.autouikit.processor.annotation.findAnnotation
import com.zest.autouikit.processor.annotation.getArgumentValue
import com.zest.autouikit.processor.utils.FunctionUtils
import kotlinx.serialization.json.Json

internal class PreviewModelGenerator(private val codeGenerator: CodeGenerator) {

    fun generate(listedFunctions: Sequence<KSFunctionDeclaration>) {
        val sourceFiles = listedFunctions.mapNotNull { it.containingFile }
        val groups = findComponents(listedFunctions)
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

    private fun findComponents(listedFunctions: Sequence<KSFunctionDeclaration>) =
        listedFunctions.map { function ->
            val functionAnnotation = function.findAnnotation(DesignComponent::class)
            val groupFromAnnotation =
                functionAnnotation?.getArgumentValue<String>("group").orEmpty()
            val nameFromAnnotation = functionAnnotation?.getArgumentValue<String>("name").orEmpty()
            val previewProviderName = getPreviewProviderClassName(function)
            val componentName = nameFromAnnotation.ifEmpty { function.toString() }
            val groupName = groupFromAnnotation.ifEmpty { componentName }

            PreviewComponent(
                className = FunctionUtils.getClass(function),
                functionName = function.toString(),
                group = groupName,
                previewProvider = previewProviderName?.asString(),
                componentName = componentName
            )
        }.groupBy { it.group }
            .map { PreviewGroup(it.key, it.value) }

    private fun getPreviewProviderClassName(function: KSFunctionDeclaration): KSName? {
        val functionParameters = function.parameters
        val providerAnnotation = functionParameters.find { it.annotations.count() > 0 }
            ?.annotations?.find { it.shortName.asString() == "PreviewParameter" }
        val providerType = providerAnnotation?.getArgumentValue<KSType>("provider")
        return providerType?.declaration?.qualifiedName
    }
}

