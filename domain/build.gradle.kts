plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(kotlin("stdlib"))

    // Non-Android coroutines core — repository interfaces expose Flow<T>/suspend fun,
    // but this module must never depend on kotlinx-coroutines-android.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // javax.inject only — NOT Hilt. Hilt is an Android DI framework and doesn't belong
    // in a pure JVM module. Use cases are annotated with @Inject constructor and wired
    // by Hilt modules that live in the `app` or `data` module instead.
    implementation("javax.inject:javax.inject:1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.9")
}
