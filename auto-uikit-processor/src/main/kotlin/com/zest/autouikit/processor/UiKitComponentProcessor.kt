package com.zest.autouikit.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import com.zest.autouikit.core.annotation.DesignComponent
import com.zest.autouikit.processor.annotation.findFunctionsWithAnnotation
import com.zest.autouikit.processor.generator.JsonPreviewModelGenerator


internal class UiKitComponentProcessor(
    val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val listedFunctions = resolver.findFunctionsWithAnnotation(DesignComponent::class)
        if (!listedFunctions.iterator().hasNext()) return emptyList()
        JsonPreviewModelGenerator(codeGenerator).generate(listedFunctions)
        return listedFunctions.filterNot { it.validate() }.toList()
    }
}
