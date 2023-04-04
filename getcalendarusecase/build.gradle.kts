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
    implementation(libs.koin.core)
    testImplementation(libs.junit)
}