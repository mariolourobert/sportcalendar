plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":commontools"))
    implementation(project(":calendarrepository"))
    implementation(libs.koin.core)
    testImplementation(libs.junit)
}