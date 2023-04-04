plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "fr.mario.sportcalendar.calendarrepository"
    compileSdk = 33
}

dependencies {
    implementation(project(":commontools"))
    implementation(project(":network"))
    implementation(project(":testtools"))
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.retrofit)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.coroutines.test)
}