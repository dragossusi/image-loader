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
    kotlin("multiplatform") version Versions.kotlin apply false
    id("org.jetbrains.compose") version Versions.compose apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}