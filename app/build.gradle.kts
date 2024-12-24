plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.belajar.newsappproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.belajar.newsappproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"API_KEY\"")
        buildConfigField("String", "BASE_URL", "\"BASE_URL\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "API_KEY", "\"API_KEY\"")
            buildConfigField("String", "BASE_URL", "\"BASE_URL\"")
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
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    //gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //swiperefreshlayout
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //webView
    implementation ("androidx.webkit:webkit:1.10.0")

    implementation  ("io.github.scwang90:refresh-layout-kernel:2.1.0")
    implementation  ("io.github.scwang90:refresh-header-classics:2.1.0")
    implementation  ("io.github.scwang90:refresh-header-radar:2.1.0")
    implementation  ("io.github.scwang90:refresh-header-falsify:2.1.0")
    implementation  ("io.github.scwang90:refresh-header-material:2.1.0")
    implementation  ("io.github.scwang90:refresh-header-two-level:2.1.0")
    implementation  ("io.github.scwang90:refresh-footer-ball:2.1.0")
    implementation  ("io.github.scwang90:refresh-footer-classics:2.1.0")
}