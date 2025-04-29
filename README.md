# ComposeAutoUiKit

Экспериментальная библиотека для автоматической генерации UiKit'а c возможностью посмотреть его на тестовой сборке.
Как аналог библиотеки [Showkase](https://github.com/airbnb/Showkase), но с возможностью вешать аннотации на любые приватные Preview. 
Для генерации используется связка KSP + Reflection

### Подключение

На время внутреннего тестирования библиотека публикуется во внутреннем GitLab Package Registry.
Для начала нужно подключить дополнительный репозиторий в `build.gradle.kts`

Далее подключаем саму библиотеку:

1. Подключаем KSP в модуле

```kotlin
plugins {
    ...
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

```
2. Подключаем зависимости

```kotlin
dependencies {

}
```
### Использование

1. Инициализируем библиотеку.

```kotlin
UiPreview.init(applicationContext)
```
2. Для того чтобы компонент попал в автогенерируемый список, нужно пометить его `Preview` функцию аннотацией `@PreviewComponent`.

```kotlin
@PreviewComponent()
@Preview
@Composable
private fun ButtonPreview() {
    val text = "Button text"
    PrimaryButton(text)
}
```
Так же дополнительно компоненты можно группировать между собой.
Для этого нужно указать название группы через `@PreviewComponent(group = "group_name)`. 

```kotlin
@PreviewComponent(group = "Buttons")
@Preview
@Composable
private fun ButtonPreview() {
    val text = "Primary Button text"
    PrimaryButton(text)
}

@PreviewComponent(group = "Buttons")
@Preview
@Composable
private fun SecondaryButtonPreview() {
    val text = "Secondary Button text"
    SecondaryButton(text)
}
```

В таком случае все компоненты с одинаковой группой будут собираться на отдельный экран. Компоненты без группы будут собираться на экране `Undefined group`.
**!Важно!** В данный момент библиотека не работает с компонентами в приватных `class` или `object`.

3. Запускаем экран с собранными `Preview` компонентами. 
Для исключения ошибок внутри самих компонентов, стоит обернуть вызов экрана библиотеки в тему проекта.

```kotlin
YourProjectTheme { 
    UiPreview.PreviewScreen() 
}
```
