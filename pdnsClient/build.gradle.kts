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
    jvm()
    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        browser()
        nodejs()
    }
    js(IR) {
        browser()
        nodejs()
    }
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    when {
        hostOs == "Mac OS X" && !isArm64 -> macosX64()
        hostOs == "Mac OS X" && isArm64 -> macosArm64()
        hostOs == "Linux" && !isArm64 -> linuxX64()
        hostOs == "Linux" && isArm64 -> linuxArm64()
        hostOs == "Windows" -> mingwX64()
        else -> throw GradleException("Host OS \"$hostOs\" is not supported in Kotlin/Native.")
    }

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmMain.dependencies {
            // put your JVM dependencies here
            implementation(libs.logback)
            implementation(libs.ktor.client.cio)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
        jsMain.dependencies {
            // put your JS dependencies here
            implementation(libs.ktor.client.js)
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.client.wasm)
            }
        }
    }
}

