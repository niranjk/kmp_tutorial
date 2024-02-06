// Section for Plugins
plugins {
    alias(libs.plugins.kotlinMultiplatform) // KMP plugin that defines this module as multiplatform module
    alias(libs.plugins.androidLibrary) // Android plugin
}

kotlin {
    // Define Android Target
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    // Defines iOS Targets
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    // SourceSet sections where we define dependencies
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlin.datetime) // Date Time Library
            implementation(libs.napier.logging) // Logging Library
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
// Define the Android sections:
android {
    namespace = "com.niranjan.khatri.kmptutorial"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
