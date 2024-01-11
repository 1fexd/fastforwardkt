package fe.buildsrc

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor

object Package {
    private const val MERGE_PKG: String = "__bundled.dependencies"

    private fun FileVisitDetails.toParentDir(): String? {
        if (relativePath.segments[0] != "META-INF") {
            return relativePath.segments.let { it.take(it.size - 1) }.joinToString(".")
        }

        return null
    }

    private fun ResolvedDependency.toPackage(): List<String> {
        return listOf(moduleGroup, moduleName, moduleVersion.replace("-", "___")).flatMap {
            it.split(".")
        }
    }

    private fun fix(pkg: List<String>): String {
        return pkg.joinToString(".") { if (it[0].isDigit()) "_$it" else it }
    }

    class Visitor(
        private val self: String,
        private val dependency: ResolvedDependency,
        private val packages: MutableMap<String, String>
    ) : FileVisitor {
        override fun visitDir(dirDetails: FileVisitDetails) {}

        override fun visitFile(fileDetails: FileVisitDetails) {
            val parentDir = fileDetails.toParentDir() ?: return

            val newPkg = listOf(
                MERGE_PKG,
                fix(self.split(".")),
                fix(dependency.toPackage()),
                parentDir
            ).joinToString(".")

            packages[parentDir] = newPkg
        }
    }

    fun Project.relocatePackages(configuration: Configuration, self: String): Map<String, String> {
        val pkgs = mutableMapOf<String, String>()

        val dependencies = configuration.resolvedConfiguration.firstLevelModuleDependencies.flatMap { dependency ->
            val visitor = Visitor(self, dependency, pkgs)
            dependency.moduleArtifacts.map { artifact -> visitor to artifact }
        }

        dependencies.forEach { (visitor, artifact) ->
            zipTree(artifact.file).visit(visitor)
        }

        return pkgs
    }
}
