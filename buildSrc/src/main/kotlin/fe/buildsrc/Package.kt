package fe.buildsrc

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor

object Package {
    fun Project.relocatePackages(configuration: Configuration, destination: String): Map<String, String> {
        val pkgs = mutableMapOf<String, String>()
        val visitor = object : FileVisitor {
            override fun visitDir(dirDetails: FileVisitDetails) {}
            override fun visitFile(fileDetails: FileVisitDetails) {
                if (fileDetails.relativePath.segments[0] != "META-INF") {
                    val pkg = fileDetails.relativePath.segments.let { it.take(it.size - 1) }.joinToString(".")
                    pkgs[pkg] = "$destination.$pkg"
                }
            }
        }

        configuration.resolvedConfiguration.firstLevelModuleDependencies.flatMap { dependencies ->
            dependencies.moduleArtifacts.map { artifact -> artifact.file }
        }.forEach { zipTree(it).visit(visitor) }

        return pkgs
    }
}
