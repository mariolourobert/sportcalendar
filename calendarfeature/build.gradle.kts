plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    namespace = "fr.mario.sportcalendar.calendarfeature"
    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":commontools"))
    implementation(project(":getcalendarusecase"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.uipreview)
    implementation(libs.androidx.compose.activity)

    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.koin.compose)
    testImplementation(libs.junit)
}