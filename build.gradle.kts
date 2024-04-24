plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.12"
}

group = "me.kervand"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("net.kyori:adventure-text-minimessage:4.16.0")
}