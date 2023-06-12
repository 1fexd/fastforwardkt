import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    java
    maven
    id("net.nemerosa.versioning") version "3.0.0"
}

group = "fe.fastforwardkt"
version = versioning.info.tag ?: versioning.info.full

repositories {
    mavenCentral()
    maven(url="https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.withType<Jar> {
    exclude("fetch_latest.sh")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
