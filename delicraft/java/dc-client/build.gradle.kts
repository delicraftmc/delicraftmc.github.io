dependencies {
    api(project(":dc-common"))
    api(project(":dc-api"))

    compileOnly(libs.hocon)
    compileOnly(libs.logback.classic)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
