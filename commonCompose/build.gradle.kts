import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.jetbrainsCompose)
	alias(libs.plugins.serialization)
}

kotlin {
	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		browser()
		nodejs()
		binaries.executable()
	}
	applyDefaultHierarchyTemplate()

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
			implementation(libs.androidx.lifecycle.viewmodel.compose)
			implementation(libs.androidx.navigation.compose)
			implementation(libs.kotlinx.serialization.core)
			implementation(libs.kotlinx.coroutines.core)
		}
		val wasmJsMain by getting {
		}
	}
}

compose.experimental {
	web.application {}
}

compose.resources {
	publicResClass = true
	packageOfResClass = "pdnsmanager.commonCompose.resources"
	generateResClass = auto
}