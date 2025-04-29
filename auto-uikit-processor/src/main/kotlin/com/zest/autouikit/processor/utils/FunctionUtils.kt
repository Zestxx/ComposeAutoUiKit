package com.zest.autouikit.processor.utils

import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

object FunctionUtils {
    internal fun getClass(componentFunction: KSFunctionDeclaration): String {
        return when (val parent = componentFunction.parent) {
            is KSClassDeclaration -> {
                require(parent.isPublic()) { "${parent.simpleName.asString()} should be public" }
                parent.qualifiedName?.asString().orEmpty()
            }

            is KSFile -> {
                val className = parent.fileName.replace(".kt", "Kt")
                "${parent.packageName.asString()}.$className"
            }

            else -> error("Could not find class for ${parent?.location}")
        }
    }
}