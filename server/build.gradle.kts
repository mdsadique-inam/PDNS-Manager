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
        languageVersion = JavaLanguageVersion.of(JavaVersion.valueOf(libs.versions.java.get()).toString())
        vendor = JvmVendorSpec.AZUL
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.pdnsClient)
    implementation(libs.kotlinx.coroutines.core)
    runtimeOnly(libs.logback)
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.koin.ktor)
    add("ksp", libs.koin.ksp)
    implementation(libs.postgresql)
    implementation(libs.hikariCP)
    implementation(libs.bundles.exposed)
    implementation(libs.bundles.security)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
}

ktor {
    docker {
        jreVersion.set(JavaVersion.valueOf(libs.versions.java.get()))
        localImageName.set("pdns-webserver")
        imageTag.set("0.0.1-preview")
    }
}