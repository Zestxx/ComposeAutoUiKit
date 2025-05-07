
# ComposeAutoUiKit

**ComposeAutoUiKit** — библиотека для автоматической генерации и визуализации коллекции ваших Jetpack Compose UI-компонентов. Позволяет удобно просматривать все компоненты и цвета в тестовой сборке, группировать их и использовать приватные preview-функции.

## Возможности

- Автоматический сбор всех компонентов, помеченных аннотацией.
- Группировка компонентов по категориям.
- Поддержка приватных preview-функций.
- Просмотр компонентов на отдельном экране.
- Простая интеграция в любой Compose-проект.

---  

## Быстрый старт

### 1. Подключение зависимостей

Добавьте в `build.gradle.kts` вашего проекта:

```kotlin  
plugins {  
    id("com.google.devtools.ksp") version "$ksp_version"
}  
```

В модуль в котором находятся UI компоненты добавьте `core` версию библиотеки и `processor`

```kotlin
dependencies {  
    implementation("com.zest.autouikit:core:$lib_version") 
    ksp("com.zest.autouikit:processor:$lib_version")
}  
```  

В модуль в котором планируется просмотр UI компонентов, добавьте в зависимости `preview` версию библиотеки

```kotlin
dependencies {  
    implementation("com.zest.autouikit:preview:$lib_version")
}  
```  

Если используется только один модуль, то все зависимости указываются вместе в одном модуле.

### 2. Использование библиотеки

Вызовите инициализацию в вашем `Application` или в `Activity` перед использованием:

####  Аннотируйте компоненты

Для включения компонента в автогенерируемый список используйте аннотацию `@DesignComponent`:

```kotlin  
@DesignComponent(group = "Buttons", name = "PrimaryButton")  
@Composable  
private fun PrimaryButtonPreview() {  
    PrimaryButton("Primary")
}  
```  

- **group** — название группы (экрана), на котором будет отображаться компонент(опционально).  .
- **name** — отображаемое имя компонента (опционально).

Можно использовать аннотацию и для публичных, и для приватных функций, а также внутри объектов и классов.

#### Пример с PreviewParameterProvider:

```kotlin  
@DesignComponent(name = "UserCard")  
@Preview  
@Composable  
fun UserCardPreview(  
    @PreviewParameter(UserCardPreviewProvider::class) user: UserCardData  
) {  
    UserCard(user = user)  
}  
```    

### 3. Просмотр компонентов

Вызовите инициализацию в вашем `Application` или в `Activity` перед использованием:

```kotlin  
    AutoUiKit.init(context)  
```  

Запустите экран с просмотром в отдельном Activity
```kotlin
    AutoUiKit.startPreviewActivity(context)
```

или как отдельный `Composable` экран

```kotlin
@Composable  
fun UiKit() {  
    AutoUiKit.PreviewScreen()  
}
```
