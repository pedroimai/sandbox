object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

    object ktx {
        const val activity = "androidx.activity:activity-ktx:${Versions.ktx.activity}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.ktx.fragment}"
        const val core = "androidx.core:core-ktx:${Versions.ktx.core}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ktx.livedata}"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktx.viewmodel}"

    }

    object hilt {
        const val core = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object test {
        const val junit = "junit:junit:${Versions.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object modules {
        const val home = ":home"
    }
}