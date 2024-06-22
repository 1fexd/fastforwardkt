import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import fe.buildsrc.Package.relocatePackages

plugins {
    kotlin("jvm") version "1.9.24"
    `java-library`
    `maven-publish`
    id("net.nemerosa.versioning") version "3.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "fe.fastforwardkt"
version = versioning.info.tag ?: versioning.info.full

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

val implementation by configurations
val shadowImplementation = configurations.create("shadowImplementation") {
    implementation.extendsFrom(this)
    isTransitive = false
}

dependencies {
    api(kotlin("stdlib"))

    implementation("fe.uribuilder:uriparser")
//    bundledDependency("com.gitlab.grrfe:gson-ext:11.0.0")
//    bundledDependency("com.google.code.gson:gson:2.10.1")
//    bundledDependency("com.github.1fexd:uriparser:0.0.7")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}



//tasks.named("jar").configure {
//    enabled = false
//}

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

//        create<MavenPublication>("shadow") {
//            groupId = project.group.toString()
//            version = project.version.toString()
//
//
//            shadow.component(this)
//        }
    }
}
