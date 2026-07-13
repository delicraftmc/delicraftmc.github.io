dependencies {
    api(project(":dc-common"))
    api(libs.slf4j.api)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.mockito.core)
    testImplementation(libs.archunit.junit5)
}
