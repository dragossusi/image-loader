import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

group = "ro.dragossusi"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    if (Features.isJvmEnabled) {
        jvm()
    }
    if (Features.isAndroidEnabled) {
        android {
            publishLibraryVariants("release", "debug")
        }
    }
    if (Features.isJsEnabled) {
        js(IR) {
            browser()
        }
    }
    if (Features.isIosEnabled) {
        ios()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
                api(compose.runtime)
                api(compose.ui)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")

                implementation("com.squareup.okio:okio:${Versions.okio}")

                implementation("io.ktor:ktor-client-core:${Versions.ktor}")
            }
        }
        if (Features.isJsEnabled) {
            val jsMain by getting {
                dependencies {
                    implementation("com.squareup.okio:okio-nodefilesystem:${Versions.okio}")
                }
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

if (Features.isAndroidEnabled) {
    apply<InstallAndroidPlugin>()
}