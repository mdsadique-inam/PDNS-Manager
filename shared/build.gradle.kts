import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.valueOf(libs.versions.java.get()).toString())
        vendor = JvmVendorSpec.AZUL
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
    }
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.ktor.resources)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.core)
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktor.client.resources.wasm)
            }
        }
    }
}

