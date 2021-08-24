plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    compileSdkVersion(Env.compileSdkVersion)
    buildToolsVersion(Env.buildToolsVersion)

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdkVersion(Env.minSdkVersion)
        targetSdkVersion(Env.targetSdkVersion)
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    kapt(Dependencies.hilt.compiler)
    testImplementation(Dependencies.test.junit)
    androidTestImplementation(Dependencies.test.androidJunit)
    androidTestImplementation(Dependencies.test.espresso)
}