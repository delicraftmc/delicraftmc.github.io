repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":dc-common"))
    api(project(":dc-api"))

    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation(libs.hikaricp)
    implementation(libs.flyway.core)
    implementation(libs.logback.classic)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
