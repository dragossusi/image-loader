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

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

kotlin {
//    ios()
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
                api(compose.runtime)
                api(compose.ui)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

                implementation("com.squareup.okio:okio:3.1.0")

                val ktor_version = "2.0.0"
                implementation("io.ktor:ktor-client-core:$ktor_version")
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}