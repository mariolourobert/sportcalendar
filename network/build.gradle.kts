plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    namespace = "fr.mario.sportcalendar.network"
}

dependencies {
    implementation(libs.koin)
    implementation(libs.okhttpinterceptor)
}