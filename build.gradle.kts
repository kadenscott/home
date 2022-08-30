plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "sh.kaden"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:4.6.4")
    implementation("com.vladsch.flexmark:flexmark-all:0.64.0")
    implementation("org.commonmark:commonmark:0.17.1")
    implementation("io.pebbletemplates:pebble:3.1.5")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4")
//    implementation("org.spongepowered:configurate-hocon:4.2.0-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    jar {
        manifest {
            attributes(
                "Main-Class" to "sh.kaden.home.Application"
            )
        }
    }

    shadowJar {
        archiveBaseName.set("home")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}