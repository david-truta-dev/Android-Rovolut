import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.parcelize)
}

val secretProperties = Properties().apply {
    load(rootProject.file("secrets.properties").inputStream())
}

val keystoreProperties = Properties().apply {
    load(rootProject.file("keystore.properties").inputStream())
}

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    namespace = "com.tdavidc.dev"
    compileSdk = 35

    defaultConfig {
        applicationId = "my.rovolut.ro"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    setFlavorDimensions(arrayListOf("environment"))
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            manifestPlaceholders["appName"] = "Rovolut Dev"
            buildConfigField(
                "String", "API_KEY",
                (System.getenv("API_KEY_PRODUCTION")
                    ?: secretProperties["apiKey.dev"]) as String
            )
        }
        create("stage") {
            dimension = "environment"
            applicationIdSuffix = ".stage"
            manifestPlaceholders["appName"] = "Rovolut Stage"
            buildConfigField(
                "String", "API_KEY",
                (System.getenv("API_KEY_PRODUCTION")
                    ?: secretProperties["apiKey.stage"]) as String
            )
        }
        create("prod") {
            dimension = "environment"
            manifestPlaceholders["appName"] = "Rovolut"
            buildConfigField(
                "String", "API_KEY",
                (System.getenv("API_KEY_PRODUCTION")
                    ?: secretProperties["apiKey.production"]) as String
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.splash)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.biometric)
    implementation(libs.retrofit)
    implementation(libs.rx.java.adapter)
    implementation(libs.retrofit.gson)
    implementation(libs.http.logging)
    implementation(libs.lottie.compose)
    implementation(libs.coil)
    implementation(libs.rx.android)
    implementation(libs.rx.kotlin)
    implementation(libs.datastore)
    implementation(libs.protobuf.serialization)
    implementation(libs.scrollingIndicator)
    implementation(libs.androidx.navigation.compose)
    // Hilt:
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.runtime.livedata)
    ksp(libs.dagger.hilt.compiler)
    // Testing and Debug Tools
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
//    debugImplementation(libs.leakcanary.android)
}
