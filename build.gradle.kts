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
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    paperDevBundle("1.19.3-R0.1-SNAPSHOT")

    api("com.laudynetwork:networkutils:latest")
    api("com.laudynetwork:database:latest")
    api("eu.thesimplecloud.simplecloud:simplecloud-api:2.4.1")
    api("com.comphenix.protocol:ProtocolLib:4.8.0")


    implementation("org.jetbrains:annotations:24.0.1")


    val scoreboardLibraryVersion = "2.0.0-RC7"
    implementation("com.github.megavexnetwork.scoreboard-library:scoreboard-library-api:$scoreboardLibraryVersion")
    runtimeOnly("com.github.megavexnetwork.scoreboard-library:scoreboard-library-implementation:$scoreboardLibraryVersion")

    runtimeOnly("com.github.megavexnetwork.scoreboard-library:scoreboard-library-v1_19_R3:$scoreboardLibraryVersion")
    runtimeOnly("com.github.megavexnetwork.scoreboard-library:scoreboard-library-packetevents:$scoreboardLibraryVersion")
}


repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://jitpack.io/")
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

tasks {
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    shadowJar {
        dependencies {
            exclude(dependency("com.comphenix.protocol:ProtocolLib:4.8.0"))
            exclude(dependency("eu.thesimplecloud.simplecloud:simplecloud-api:2.4.1"))
            exclude(dependency("com.laudynetwork:networkutils:latest"))
            exclude(dependency("com.laudynetwork:database:latest"))
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("dist/mlgRush.jar"))
    }
}
