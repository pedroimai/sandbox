object Env {
    const val compileSdkVersion = 33
    const val buildToolsVersion = "33.0.1"
    const val minSdkVersion = 22
    const val targetSdkVersion = 33

    object Build {
        private const val major = 1
        private const val minor = 0
        private const val patch = 0
        private const val build = 0 // bump for dogfood builds, public betas, internal test track, etc.

        const val versionCode = (major * 1000000) + (minor * 10000) + (patch * 100) + build
        const val versionName = "$major.$minor.$patch"
    }
}