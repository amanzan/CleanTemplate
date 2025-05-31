# 🎩 Android Jetpack Compose Project Guidelines

*Last updated: 2025-05-31*
*Version: 1.0*

## 🔝 Flexibility Notice

* This is a recommended project structure, but be flexible and adapt to existing project structures.
* Do not enforce these patterns if the project already follows a different organization.
* Focus on maintaining **consistency** while applying **Jetpack Compose best practices**.

---

## 🏗️ Project Architecture & Best Practices

* Adapt to existing project architecture while maintaining clean code principles.
* Follow Material Design 3 guidelines and components.
* Implement clean architecture with **domain**, **data**, and **presentation** layers.
* Use **Kotlin coroutines** and **Flow** for async operations.
* Use **Hilt** for dependency injection.
* Follow **unidirectional data flow** with `ViewModel` and UI state.
* Use **Jetpack Navigation-Compose** for screen management.
* Apply proper **state hoisting** and composition techniques.

---

## 📁 Recommended Folder Structure

```
app/
  src/
    main/
      java/com/yourpackage/
        data/
          repository/
          datasource/
          models/
        domain/
          usecases/
          models/
          repository/
        presentation/
          screens/
          components/
          theme/
          viewmodels/
        di/
        utils/
      res/
        values/
        drawable/
        mipmap/
    test/
    androidTest/
```

---

## 🎨 Jetpack Compose UI Guidelines

1. Use `remember` and `derivedStateOf` properly.
2. Optimize recomposition scopes.
3. Respect modifier order (e.g., `padding` before `background`).
4. Use consistent and descriptive function names.
5. Add preview annotations for reusable composables.
6. Use `MutableState` or `StateFlow` to manage state.
7. Handle loading and error UI states clearly.
8. Use `MaterialTheme` for theming.
9. Follow accessibility practices.
10. Use Compose animation APIs when relevant.

---

## 🧪 Testing Guidelines

* Write **unit tests** for `ViewModel`s and use cases.
* Use **Compose UI testing** with assertions on nodes.
* Provide **fake repositories** or test doubles for isolation.
* Keep good **test coverage** on business logic.
* Use proper **coroutine dispatchers** in tests (`TestDispatcher`).

---

## ⚙️ Performance Guidelines

* Avoid unnecessary recompositions using `key`, `remember`, etc.
* Use `LazyColumn` and `LazyRow` for lists.
* Load images efficiently (e.g., Coil, Glide).
* Structure state to avoid full recomposition on minor changes.
* Respect lifecycle-aware components (e.g., `LaunchedEffect`).
* Be aware of memory usage.
* Offload work to background threads when needed.

---

## 🥷 Dependency Injection with Hilt

### ✅ General Guidelines

* Use `@Binds` for interface-to-implementation bindings (more efficient).
* Use `@Provides` for:

    * Third-party types (e.g., `OkHttpClient`, `Retrofit`)
    * Classes requiring special construction logic.
* Annotate with `@Singleton` or appropriate scope.
* Prefer `@Inject` constructors when possible.

### 📆 Example Modules in This Project

#### `AppModule.kt`

```kotlin
@Provides
@Singleton
fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>
```

#### `DatabaseModule.kt`

```kotlin
@Provides
@Singleton
fun provideAppDatabase(context: Context): AppDatabase
@Provides
fun providePartnerDao(db: AppDatabase): PartnerDao
```

#### `NetworkModule.kt`

```kotlin
@Provides
@Singleton
fun provideOkHttpClient(): OkHttpClient

@Provides
@Singleton
fun provideRetrofit(): Retrofit

@Provides
@Singleton
fun provideApiService(retrofit: Retrofit): ApiService
```

#### `RepositoryModule.kt`

```kotlin
@Binds
@Singleton
abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

@Binds
@Singleton
abstract fun bindPartnerRepository(impl: PartnerRepositoryImpl): PartnerRepository
```

---

## ✅ DI Folder Placement

**Recommended location for the `di/` folder:**

```
com/yourpackage/
  di/
```

✅ This keeps DI concerns at the same level as `data`, `domain`, and `presentation`.

Just update imports after moving — there are no functional side effects.

---
