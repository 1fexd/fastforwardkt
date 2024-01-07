import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.22"
    `java-library`
    `maven-publish`
    id("net.nemerosa.versioning") version "3.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "fe.fastforwardkt"
version = versioning.info.tag ?: versioning.info.full

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

val implementation: Configuration by configurations
val shadowImplementation: Configuration by configurations.creating
implementation.extendsFrom(shadowImplementation)

dependencies {
    api(kotlin("stdlib"))

    shadowImplementation("com.github.1fexd:uriparser:0.0.7")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

val shadowJarTask = tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()
    exclude("META-INF/**/*", "*.sh")

    val pkgs = mutableSetOf<String>()
    val visitor = object : FileVisitor {
        override fun visitDir(dirDetails: FileVisitDetails) {}
        override fun visitFile(fileDetails: FileVisitDetails) {
            if (fileDetails.relativePath.segments[0] != "META-INF") {
                pkgs.add(fileDetails.relativePath.segments.let { it.take(it.size - 1) }.joinToString("."))
            }
        }
    }

    shadowImplementation.resolvedConfiguration.firstLevelModuleDependencies.flatMap { dependencies ->
        dependencies.moduleArtifacts.map { artifact -> artifact.file }
    }.forEach { zipTree(it).visit(visitor) }

    pkgs.forEach { pkg -> relocate(pkg, "${project.group}.internal$pkg") }

    archiveClassifier.set("")
    minimize()
    configurations = listOf(shadowImplementation)
}

tasks.named("jar").configure {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            setArtifacts(listOf(shadowJarTask.get()))
            groupId = project.group.toString()
            version = project.version.toString()
        }
    }
}

tasks.whenTaskAdded {
    if (name == "generateMetadataFileForPluginShadowPublication") {
        dependsOn(shadowJarTask)
    }
}
