
object Versions {

    const val androidMinSDK = 21
    const val androidCompileSDK = 29
    const val androidTargetSDK = 29
    const val androidVersionCode = 1
    const val androidVersionName = "0.0.1"
    const val androidBuildTools = "29.0.2"

    const val kotlin = "1.4.10"

    object AndroidX {
        const val appCompat = "1.2.0"
        const val coreKtx = "1.3.1"
        const val constraintLayout = "1.1.3"
        const val material = "1.2.0"
        const val recyclerView = "1.2.0"
    }
}

object Dependencies {

    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
    const val material = "com.google.android.material:material:${Versions.AndroidX.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
}

