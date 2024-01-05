import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    application
}

group = "mdsadiqueinam.github.io"
version = "1.0.0"

kotlin {
    val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val target = "native"
    val nativeTarget = when {
        hostOs == "Mac OS X" && arch == "x86_64" -> macosX64(target)
        hostOs == "Mac OS X" && arch == "aarch64" -> macosArm64(target)
        hostOs == "Linux" -> linuxX64(target)
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "mdsadiqueinam.github.io.main"
            }
        }
    }
    jvm {
        application {
            mainClass = "mdsadiqueinam.github.io.mainKt"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.logback)
                implementation(libs.bundles.ktor.server)
                implementation(libs.bundles.koin)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.ktor.server.tests)
                implementation(libs.kotlin.test.junit)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.postgresql)
                implementation(libs.bundles.exposed)
            }
        }
        val nativeMain by getting
        val nativeTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor.server.test.host)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp)
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        println("SourceJarTask====>${it.name}")
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}
