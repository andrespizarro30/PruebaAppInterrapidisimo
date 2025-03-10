plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.afsoftwaresolutions.pruebaappinterrapidisimo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.afsoftwaresolutions.pruebaappinterrapidisimo"
        minSdk = 24
        targetSdk = 35
        versionCode = 101
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://testing.interrapidisimo.co:8088\"")
        }
        getByName("debug") {
            isDebuggable = true
            resValue("string", "APP_NAME", "[DEBUG] PruebaAPPInterrap")
            buildConfigField("String", "BASE_URL", "\"https://testing.interrapidisimo.co:8088\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    val navVersion = "2.7.1"
    val retrofitVersion = "2.9.0"
    val daggerHiltVersion = "2.48"
    val roomVersion = "2.6.1"

    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //DaggerHilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-compiler:$daggerHiltVersion")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.3.1")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Unit testing
    // JUnit
    testImplementation("junit:junit:4.13.2")

    // Mockito for mocking dependencies
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")

    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    // Mockk (alternative to Mockito, if preferred)
    testImplementation("io.mockk:mockk:1.13.5")

    // AndroidX Core Testing for LiveData and StateFlow testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("app.cash.turbine:turbine:1.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}