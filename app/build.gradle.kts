plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(Env.compileSdkVersion)
    buildToolsVersion(Env.buildToolsVersion)

    defaultConfig {
        applicationId = "com.pedroimai.sandbox"
        minSdkVersion(Env.minSdkVersion)
        targetSdkVersion(Env.targetSdkVersion)
        versionCode = Env.Build.versionCode
        versionName = Env.Build.versionName


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
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
    implementation(Dependencies.ktx.livedata)
    implementation(Dependencies.ktx.viewmodel)

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
    implementation(project(Dependencies.modules.home))
}