plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

group = "com.laudynetwork.mlgrush"
version = System.getenv("RELEASE_VERSION") ?: "1.0.0"
description = "mlgrush Plugin for LaudyNetwork"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    paperDevBundle("1.19.3-R0.1-SNAPSHOT")

    implementation("org.jetbrains:annotations:24.0.1")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.cloudnetservice.eu/repository/releases/")
    maven("https://repo.dmulloy2.net/repository/public/")
}
