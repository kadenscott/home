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
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    jar {
        manifest {
            attributes(
                "Main-Class" to "sh.kaden.home.Entrypoint"
            )
        }
    }

    shadowJar {
        archiveBaseName.set("home")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}