plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("com.github.ben-manes.versions")
}

android {
    compileSdk = Env.compileSdkVersion
    buildToolsVersion = Env.buildToolsVersion

    defaultConfig {
        applicationId = "com.pedroimai.sandbox"
        minSdk = Env.minSdkVersion
        targetSdk = Env.targetSdkVersion
        versionCode = Env.Build.versionCode
        versionName = Env.Build.versionName


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    namespace = "com.pedroimai.sandbox"
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {
    implementation(Dependencies.kotlin)
    implementation(Dependencies.appcompat)

    //layout
    implementation(Dependencies.material)
    implementation(Dependencies.constraintlayout)

    //ktx
    implementation(Dependencies.ktx.activity)
    implementation(Dependencies.ktx.fragment)
    implementation(Dependencies.ktx.core)
    implementation(Dependencies.ktx.lifecycle.viewmodel)
    implementation(Dependencies.ktx.lifecycle.runtime)

    //navigation
    implementation(Dependencies.navigation.fragment)
    implementation(Dependencies.navigation.ui)

    //hilt
    implementation(Dependencies.hilt.core)
    kapt(Dependencies.hilt.compiler)
    testImplementation(Dependencies.test.junit)
    androidTestImplementation(Dependencies.test.androidJunit)
    androidTestImplementation(Dependencies.test.espresso)

    //modules
    implementation(project(Dependencies.modules.comics))
    implementation(project(Dependencies.modules.characters))
    implementation(project(Dependencies.modules.home))
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}