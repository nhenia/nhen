// Top-level build file where you can add configuration options common to all sub-projects/modules.
    plugins {
        alias(libs.plugins.androidApplication) apply false
        alias(libs.plugins.kotlinAndroid) apply false
        alias(libs.plugins.ksp) apply false
        id("org.jetbrains.kotlin.plugin.compose") version "2.2.0" apply false
    }


