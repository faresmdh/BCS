import java.util.regex.Pattern.compile


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "2.0.0"
}


android {
    namespace = "m.ify.computersciencebouira"
    compileSdk = 34

    defaultConfig {
        applicationId = "m.ify.computersciencebouira"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "2.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug{
            ndk{
                debugSymbolLevel = "FULL"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.liquid.swipe)
    implementation (libs.lottie)
    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.1"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.ktor:ktor-client-android:3.0.0")
    implementation (libs.gson)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.iamyashchouhan:AndroidPdfViewer:1.0.3")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
    implementation("com.itextpdf:itext7-core:7.2.5") // Update the version as per latest available




}