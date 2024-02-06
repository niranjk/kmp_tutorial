// Android needs the application and Android Kotlin Plugins
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}
// Here we have all android specific settings:
android {
    namespace = "com.niranjan.khatri.kmptutorial.android"
    compileSdk = 34 // Android SDK version to compile against
    defaultConfig {
        applicationId = "com.niranjan.khatri.kmptutorial.android" // ID for your Android App (unique)
        minSdk = 24 // lowest Android version your app will run on.
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // set this to true when you're ready to release
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared) // android depends on the shared module (where all shared business logic reside)
    // These below are all android-specific libraries
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
}