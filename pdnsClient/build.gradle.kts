import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    jvm()
    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.serialization.core)
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.client.wasm)
            }
        }
    }
}

