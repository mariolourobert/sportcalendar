plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    namespace = "fr.mario.sportcalendar.getcalendarusecase"
    compileSdk = 33
}

dependencies {
    implementation(project(":commontools"))
    implementation(project(":calendarrepository"))
    implementation(project(":testtools"))
    implementation(libs.koin.core)
    implementation(libs.androidx.annotation)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.coroutines.test)
}