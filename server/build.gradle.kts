plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    application
}

group = "mdsadiqueinam.github.io"
version = "1.0.0"

application {
    mainClass.set("mdsadiqueinam.github.io.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_20.toString())
        vendor = JvmVendorSpec.AZUL
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.pdnsClient)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.logback)
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.koin)
    add("ksp", libs.koin.ksp)
    implementation(libs.postgresql)
    implementation(libs.bundles.exposed)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
}

