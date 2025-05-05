package com.zest.autouikit.processor.annotation

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import kotlin.reflect.KClass

fun Resolver.findFunctionsWithAnnotation(kClass: KClass<*>): Sequence<KSFunctionDeclaration> {
    return getSymbolsWithAnnotation(kClass.qualifiedName.toString())
        .filterIsInstance<KSFunctionDeclaration>()
}

fun KSDeclaration.findAnnotation(kClass: KClass<*>): KSAnnotation? {
    return annotations.find { annotation ->
        annotation.shortName.asString() == kClass.simpleName
    }
}

fun <T> KSAnnotation.getArgumentValue(argumentName: String): T? {
    return arguments.find { it.name?.asString() == argumentName }?.value as? T
}