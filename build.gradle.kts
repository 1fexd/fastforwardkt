plugins {
    kotlin("jvm") version "1.8.21"
    java
    `maven-publish`
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()

            from(components["java"])
        }
    }
}

