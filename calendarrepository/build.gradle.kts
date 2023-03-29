plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":commontools"))
    implementation(project(":network"))
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit)
    testImplementation(libs.junit)
}