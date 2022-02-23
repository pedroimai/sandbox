object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

    object ktx {
        const val activity = "androidx.activity:activity-ktx:${Versions.ktx.activity}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.ktx.fragment}"
        const val core = "androidx.core:core-ktx:${Versions.ktx.core}"

        object lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktx.lifecycle}"
            const val viewmodel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktx.lifecycle}"
        }
    }

    object hilt {
        const val core = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    // Kotlin
    object navigation {
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val dynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
        const val testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
    }

    object test {
        const val junit = "junit:junit:${Versions.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object modules {
        const val characters = ":characters"
        const val comics = ":comics"
        const val home = ":home"
        const val shared = ":shared"
    }
}