plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.koin.core)

    implementation(libs.kotlinx.coroutines.core) // or your preferred version
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation (libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}