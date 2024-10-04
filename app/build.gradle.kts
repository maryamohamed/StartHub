plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.training.starthub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.training.starthub"
        minSdk = 29
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.databinding:viewbinding:8.5.0")

    // recycler view
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    // Lottie
    implementation("com.airbnb.android:lottie:6.3.0")

    // layout
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.8.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // navigation fragment
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // live data & view model
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    //room database
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.5.1")
}