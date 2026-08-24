// Top-level build file — declares plugin versions once, applied per-module without
// a version (see any module's build.gradle.kts, e.g. core/database/build.gradle.kts).
// This file is why CI failed at configuration time: without a version declared here,
// `id("com.android.library")` etc. in every module has nothing to resolve against.

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("com.android.library") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.5"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(files("$rootDir/detekt.yml"))
        source.setFrom(files("src/main/kotlin", "src/main/java"))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
