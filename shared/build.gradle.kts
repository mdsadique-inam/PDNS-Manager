import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val target = "native"
    when {
        hostOs == "Mac OS X" && arch == "x86_64" -> macosX64(target)
        hostOs == "Mac OS X" && arch == "aarch64" -> macosArm64(target)
        hostOs == "Linux" -> linuxX64(target)
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
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
            implementation(libs.kotlinx.serialization.core)
        }
        val wasmJsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-resources:3.0.0-wasm2")
            }
        }
    }
}

