plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("com.github.johnrengelman.shadow") version ("7.1.2")
}

group = "com.laudynetwork.mlgrush"
version = System.getenv("RELEASE_VERSION") ?: "1.0.0"
description = "mlgrush Plugin for LaudyNetwork"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    paperDevBundle("1.19.3-R0.1-SNAPSHOT")

    implementation("com.laudynetwork:networkutils:latest")
    implementation("com.laudynetwork:database:latest")

    implementation("org.jetbrains:annotations:24.0.1")


//    val scoreboardLibraryVersion = "main-SNAPSHOT"
//    implementation("com.github.megavexnetwork.scoreboard-library:scoreboard-library-api:$scoreboardLibraryVersion")
//    implementation("com.github.megavexnetwork.scoreboard-library:scoreboard-library-implementation:$scoreboardLibraryVersion")
//    implementation("com.github.megavexnetwork.scoreboard-library:scoreboard-library-v1_19_R3:$scoreboardLibraryVersion")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
    maven("https://repo.dmulloy2.net/repository/public/")
//    maven("https://jitpack.io/")
    maven {
        url = uri("https://repo.laudynetwork.com/repository/maven")
        authentication {
            create<BasicAuthentication>("basic")
        }
        credentials {
            username = System.getenv("NEXUS_USER")
            password = System.getenv("NEXUS_PWD")
        }
    }
}