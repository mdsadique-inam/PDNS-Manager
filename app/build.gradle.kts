import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "app"
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                        add(project.projectDir.path + "/commonMain/")
                        add(project.projectDir.path + "/wasmJsMain/")
                    }
                    proxy = (proxy ?: mutableMapOf()).apply {
                        put("/api", mapOf(
                            "target" to "http://localhost:8000",
                            "pathRewrite" to mapOf(
                                "/api" to ""
                            )
                        ))
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(projects.commonCompose)
            implementation(projects.shared)
            implementation(projects.pdnsClient)
            implementation(projects.koinCompose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.bundles.ktor.client.wasm)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

val copyResources by tasks.register<Copy>("copyResources") {
    from("${rootProject.projectDir}/commonCompose/src/commonMain/composeResources")
    into("${projectDir}/src/commonMain/composeResources")
}

tasks.named("convertXmlValueResourcesForCommonMain").configure {
    dependsOn(copyResources.path)
}

tasks.all {
    if (path != copyResources.path) {
        mustRunAfter(copyResources.path)
    }
}