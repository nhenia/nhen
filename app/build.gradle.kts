plugins {
    alias(libs.plugins.androidApplication)
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)

}

    android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("G:\\My Drive\\az_apk_keystore.jks")
            storePassword = "18187077190901818"
            keyPassword = "18187077190901818"
            keyAlias = "key0"
        }
    }
        namespace = "com.hereliesaz.nhen"
    compileSdk = 36

    defaultConfig {
      applicationId = "com.hereliesaz.nhen"
        minSdk = 31
        targetSdk = 36
    versionCode = 2
    versionName = "2.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
       release {
           isMinifyEnabled = false
           proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
       }
    }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }


        kotlinOptions {
            jvmTarget = "17"
        }
        buildToolsVersion = "36.0.0"
    }

  dependencies {
      implementation(libs.material)
      implementation(libs.androidx.appcompat)
      // Jetpack Compose BOM (Bill of Materials) - ensures compatible versions
      val composeBom = platform(libs.composeBom) // Or the latest stable version
      implementation(composeBom)
      androidTestImplementation(composeBom)

      // Essential Compose libraries
      implementation(libs.androidx.ui)
      implementation(libs.androidx.ui.graphics) // This is where androidx.compose.ui.graphics.Color comes from
      implementation(libs.androidx.ui.tooling.preview)
      implementation(libs.androidx.material3) // Or material if you are using Material Design 2

      // Optional - Other Compose libraries you might need
       implementation(libs.androidx.activity) // Or the latest stable version
       implementation(libs.androidx.lifecycle.viewmodel.compose) // Or the latest stable version

      // UI Tests
      androidTestImplementation(libs.androidx.ui.test.junit4)
      debugImplementation(libs.androidx.ui.tooling)
      debugImplementation(libs.androidx.ui.test.manifest)
  }
