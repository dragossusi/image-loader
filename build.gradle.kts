import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}

plugins {
    kotlin("multiplatform") version "1.6.20" apply false
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev675" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}