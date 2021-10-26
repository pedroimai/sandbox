plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    compileSdk = Env.compileSdkVersion
    buildToolsVersion = Env.buildToolsVersion

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = Env.minSdkVersion
        targetSdk = Env.targetSdkVersion
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

    //hilt
    implementation(Dependencies.hilt.core)
    kapt(Dependencies.hilt.compiler)
    testImplementation(Dependencies.test.junit)
    androidTestImplementation(Dependencies.test.androidJunit)
    androidTestImplementation(Dependencies.test.espresso)

    implementation(project(Dependencies.modules.shared))
}