package com.zest.autouikit.reflection

import androidx.compose.runtime.Composer
import androidx.compose.runtime.reflect.ComposableMethod
import androidx.compose.runtime.reflect.getDeclaredComposableMethod
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zest.autouikit.core.models.PreviewComponent
import java.lang.reflect.Modifier
import kotlin.reflect.KType
import kotlin.reflect.typeOf

internal object CommonPreviewUtils {

    fun invokeComposableViaReflection(
        composableMethod: ComposableMethod,
        composer: Composer,
        argument: Any? = null
    ) {
        try {
            composableMethod.asMethod().isAccessible = true
            if (Modifier.isStatic(composableMethod.asMethod().modifiers)) {
                composableMethod.invoke(composer, null, argument)
            } else {
                val instance = getInstance(composableMethod.asMethod().declaringClass)
                composableMethod.invoke(composer, instance, argument)
            }
        } catch (e: ReflectiveOperationException) {
            throw ClassNotFoundException(
                "Composable Method '${composableMethod.asMethod().name}' not found",
                e
            )
        }
    }

    private fun getInstance(composableClass: Class<*>): Any? {
        val objectClassField = composableClass.fields.find { it.name == "INSTANCE" }
        return if (objectClassField != null) {
            objectClassField.get(null)
        } else {
            composableClass.getConstructor().newInstance()
        }
    }

    fun getPreviewProviderParameters(
        parameterProviderClass: Class<out PreviewParameterProvider<Any>>?,
        parameterProviderIndex: Int
    ): Array<Any?> {
        if (parameterProviderClass != null) {
            try {
                val constructor =
                    parameterProviderClass.constructors
                        .singleOrNull { it.parameterTypes.isEmpty() }
                        ?.apply { isAccessible = true }
                        ?: throw IllegalArgumentException(
                            "PreviewParameterProvider constructor can not" + " have parameters"
                        )
                val params = constructor.newInstance() as PreviewParameterProvider<*>
                if (parameterProviderIndex < 0) {
                    return params.values.toArray(params.count)
                }
                return listOf(params.values.elementAt(parameterProviderIndex))
                    .map { unwrapIfInline(it) }
                    .toTypedArray()
            } catch (e: KotlinReflectionNotSupportedError) {
                // kotlin-reflect runtime dependency not found. Suggest adding it.
                throw IllegalStateException(
                    "Deploying Compose Previews with PreviewParameterProvider " +
                            "arguments requires adding a dependency to the kotlin-reflect library.\n" +
                            "Consider adding 'debugImplementation " +
                            "\"org.jetbrains.kotlin:kotlin-reflect:\$kotlin_version\"' " +
                            "to the module's build.gradle."
                )
            }
        } else {
            return emptyArray()
        }
    }

    private fun unwrapIfInline(classToCheck: Any?): Any? {
        // At the moment is not possible to use classToCheck::class.isValue, even if it works when
        // running tests, is not working once trying to run the Preview instead.
        // it would be possible in the future.
        // see also https://kotlinlang.org/docs/inline-classes.html
        if (classToCheck != null && classToCheck::class.java.annotations.any { it is JvmInline }) {
            // The first primitive declared field in the class is the value wrapped
            val fieldName: String =
                classToCheck::class.java.declaredFields.first { it.type.isPrimitive }.name
            return classToCheck::class
                .java
                .getDeclaredField(fieldName)
                .also { it.isAccessible = true }
                .get(classToCheck)
        }
        return classToCheck
    }

    private fun Sequence<Any?>.toArray(size: Int): Array<Any?> {
        val iterator = iterator()
        return Array(size) { iterator.next() }
    }
}

internal fun String.asPreviewProviderClass(): Class<out PreviewParameterProvider<Any>>? {
    return Class.forName(this) as? Class<out PreviewParameterProvider<Any>>
}

internal fun Class<*>.getComposableMethod(
    component: PreviewComponent,
    previewParameterType: Class<*>
): ComposableMethod? {
    val isCollection = Collection::class.java.isAssignableFrom(previewParameterType)
    val composableClass = this
    val composableMethod = try {
        composableClass.getDeclaredComposableMethod(
            component.functionName,
            previewParameterType
        )
    } catch (_: NoSuchMethodException) {
        previewParameterType.superclass?.let { clazz ->
            composableClass.getComposableMethod(
                component,
                clazz
            )
        }
    }
    return composableMethod
}

internal fun extractElementTypeFromCollection(list: Collection<Any?>): Class<*>? {
    return list.firstOrNull()?.javaClass
}

inline fun <reified T> extractGenericElementType(): KType {
    val type = typeOf<T>()
    return (type.arguments.first().type ?: error("No generic arg"))
}