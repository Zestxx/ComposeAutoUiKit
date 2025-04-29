package com.zest.autouikit.processor.generator


import com.zest.autouikit.processor.annotation.findAnnotation
import com.zest.autouikit.processor.annotation.getArgumentValue
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.zest.autouikit.core.annotation.DesignComponent
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ksp.writeTo

@Deprecated("Legacy class. Should be removed.")
internal class SimpleComponentListGenerator(private val codeGenerator: CodeGenerator) {

    fun generate(listedFunctions: Sequence<KSFunctionDeclaration>) {
        val componentImports = listedFunctions
            .onEach {
                require(it.isPublic() && it.parameters.isEmpty()) {
                    "Component $it should be public and don't have any arguments"
                }
            }
            .map { it.packageName.asString() to it.simpleName.asString() }

        val fileSpec = FileSpec.builder(
            packageName = "com.redmadrobot.preview", fileName = "UiKitComponentList"
        )
            .addImport("androidx.compose.foundation.layout", "Column", "Spacer", "padding", "height", "fillMaxSize")
            .addImport("androidx.compose.foundation.lazy", "LazyColumn")
            .addImport("androidx.compose.material", "Divider", "Text")
            .addImport("androidx.compose.ui", "Modifier", "unit.dp")
            .apply {
                componentImports.forEach { (packageName, name) -> addImport(packageName, name) }
            }
            .addFunction(getUiKitComponentListFunction(listedFunctions))
            .build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    private fun getUiKitComponentListFunction(
        listedFunctions: Sequence<KSFunctionDeclaration>
    ): FunSpec {

        val itemCode: (KSFunctionDeclaration) -> String = { componentFunction ->
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
                    |               Text("$infoText", modifier = Modifier.padding(16.dp))
                    |               Spacer(modifier = Modifier.height(10.dp))
                    |               $componentFunction()
                    |               Divider(modifier = Modifier.padding(top = 16.dp))
                    |           }
                    |       }
                    |
                """.trimMargin()
        }

        return FunSpec.builder("UiKitComponentList")
            .addAnnotation(
                AnnotationSpec.builder(
                    ClassName("androidx.compose.runtime", "Composable")
                ).build()
            )
            .addStatement("LazyColumn(modifier = Modifier.fillMaxSize()) {")
            .apply {
                listedFunctions.forEach {
                    addStatement(itemCode(it))
                }
            }
            .addStatement("}")
            .build()
    }
}

