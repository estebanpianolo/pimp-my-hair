plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.androidCompileSDK)
    buildToolsVersion(Versions.androidBuildTools)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "com.etienne.pimpmyhair"

        versionCode = Versions.androidVersionCode
        versionName = Versions.androidVersionName
        minSdkVersion(Versions.androidMinSDK)
        targetSdkVersion(Versions.androidTargetSDK)

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    packagingOptions {
        exclude("META-INF/services/javax.annotation.processing.Processor")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
        exclude("LICENSE.txt")
        exclude("META-INF/rxjava.properties")
        exclude("project.clj")
    }

    flavorDimensions("default")
}

dependencies {
    implementation(Dependencies.stdLib)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)

    implementation(Dependencies.Rx.java)
    implementation(Dependencies.Rx.android)
    implementation(Dependencies.Rx.kotlin)

    kapt(Dependencies.Dagger.compiler)
    implementation(Dependencies.Dagger.dagger)
    implementation(Dependencies.Dagger.android)

    implementation(project(":libraries:archi"))
    implementation(project(":libraries:pratik"))

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
