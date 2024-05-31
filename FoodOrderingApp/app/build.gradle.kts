plugins {
    alias(libs.plugins.androidApplication)
    //jetpack navigation
    id("androidx.navigation.safeargs")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.foodorderingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodorderingapp"
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

    buildFeatures {
        dataBinding = true
    }

    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/ASL2.0")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.recyclerview)
    implementation(libs.firebase.storage)
    implementation(fileTree(mapOf(
        "dir" to "zalopay",
        "include" to listOf("*.aar", "*.jar")
    )))
    testImplementation(libs.junit)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:3.12.4")
    // Firebase Android testing
//    androidTestImplementation ("com.google.firebase:firebase-firestore-testing:latest_version_here")


    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Glidle
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //jetpack navigation
    val nav_version = "2.7.7"

    // Java language implementation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-messaging")

    // import swipe refresh layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // momo payment mobile-sdk
    implementation ("com.github.momo-wallet:mobile-sdk:1.0.7")

    // zalo
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")

    // google credentical
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.1.0")
    implementation ("com.google.api-client:google-api-client:1.33.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation("com.github.PhilJay:MPAndroidChart:3.1.0")

    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:20.0.0")

}
