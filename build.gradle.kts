plugins {
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.dagger.hilt.gradle)
        classpath(libs.oss.licenses.plugin)
    }
}

tasks.getByName<Wrapper>("wrapper") {
    gradleVersion = "8.7"
    distributionType = Wrapper.DistributionType.ALL
}
