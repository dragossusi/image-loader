import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("com.android.library") apply Features.isAndroidEnabled
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
            binaries.executable()
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
                api(compose.material)

                //todo
//                @OptIn(ExperimentalComposeLibrary::class)
//                api(compose.material3)

                api(project(":kil"))
                api(project(":kil-compose"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")

                implementation("com.squareup.okio:okio:${Versions.okio}")

                implementation("io.ktor:ktor-client-core:${Versions.ktor}")
            }
        }
        if (Features.isJsEnabled) {
            val jsMain by getting {
                dependencies {
                    implementation(compose.web.core)
                    implementation("com.squareup.okio:okio-nodefilesystem:${Versions.okio}")
                    implementation("io.ktor:ktor-client-js:${Versions.ktor}")
                }
            }
        }
        if (Features.isJvmEnabled) {
            val jvmMain by getting {
                dependencies {
                    implementation(compose.desktop.currentOs)
                    implementation("io.ktor:ktor-client-cio:${Versions.ktor}")
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