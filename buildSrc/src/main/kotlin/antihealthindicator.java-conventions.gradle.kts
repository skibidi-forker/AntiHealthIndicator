plugins {
    java
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-releases/")
}

java {
    disableAutoTargetJvm()
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release = 8
    }

    defaultTasks("build")
}