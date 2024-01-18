plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    jvm()
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.cio)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmMain.dependencies {
            // put your JVM dependencies here
            implementation(libs.logback)
        }
    }
}

