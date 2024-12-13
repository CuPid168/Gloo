plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.dicoding.gloo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dicoding.gloo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation (libs.androidx.cardview)
    implementation(libs.androidx.activity)

    // Retrofit and Networking
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // CameraX
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

    // Image Loading
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    ksp (libs.ksp)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //tensorflow
    implementation (libs.tensorflow.lite.support)
    implementation (libs.tensorflow.lite.metadata)
    implementation (libs.tensorflow.lite.task.vision)

    implementation (libs.androidx.cardview)

    implementation (libs.guava)

    // Room
    implementation (libs.androidx.room.runtime)
    ksp (libs.room.compiler)
    implementation (libs.androidx.room.ktx)
    annotationProcessor (libs.androidx.room.compiler.v240beta01)

    // Kotlin Symbol Processing (KSP)
    ksp (libs.room.compiler.v252)

    // Kotlin
    implementation (libs.kotlin.stdlib)
}