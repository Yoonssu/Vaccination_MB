plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)

    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.vaccination"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vaccination"
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
    viewBinding{
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.identity.credential.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.preference:preference:1.2.1")


    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// gson
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // tikxml
    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("com.tickaroo.tikxml:processor:0.8.13")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

    implementation("androidx.multidex:multidex:2.0.1")

    implementation("com.google.android.gms:play-services-auth:21.1.1")

    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")


    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

    //MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //네이버
    implementation("com.navercorp.nid:oauth:5.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")



//    //카카오
//    // Kakao Login
//    implementation ("com.kakao.sdk:v2-user:2.10.0")
//    //implementation ("com.kakao.sdk:v2-user:2.3.0") // 카카오 로그인
//    implementation ("com.kakao.sdk:usermgmt:1.27.0")// 카카오 로그인 버튼 만들 때 사용



}