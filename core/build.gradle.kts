plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.thelazyproject.mbaca.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)

    api(libs.hilt.android)
    implementation(libs.feature.delivery.ktx)
    ksp(libs.hilt.compiler)

    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.okhttp.logging)

    api(libs.room.runtime)
    api(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.sqlcipher.android)
    api(libs.androidx.sqlite)

    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.runtime.ktx)

    api(libs.coroutines.core)
    api(libs.coroutines.android)

    api(libs.glide)
    ksp(libs.glide.compiler)

    api(libs.androidx.recyclerview)
    api(libs.androidx.cardview)
    api(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}











