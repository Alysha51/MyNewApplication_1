plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

//


// Project build.gradle.kts file.

android {
    namespace = "com.example.adminapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.adminapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//    android {
//        applicationVariants.all {
//            // Return compile configuration objects of a variant.
//            compileConfiguration.resolutionStrategy {
//                // Use Gradle's ResolutionStrategy API
//                // to customize how this variant resolves dependencies.
//                ...
//            }
//            // Return runtime configuration objects of a variant.
//            runtimeConfiguration.resolutionStrategy {
//                ...
//            }
//            // Return annotation processor configuration of a variant.
//            annotationProcessorConfiguration.resolutionStrategy {
//                ...
//            }
//        }
//    }
    android {
        dependenciesInfo {
            // Disables dependency metadata when building APKs.
            includeInApk = false
            // Disables dependency metadata when building Android App Bundles.
            includeInBundle = false
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.squareup.picasso:picasso:2.71828")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.code.gson:gson:2.8.6")
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage:20.2.1")
    implementation("androidx.core:core-ktx:+")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}