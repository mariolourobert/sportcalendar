plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx)
}