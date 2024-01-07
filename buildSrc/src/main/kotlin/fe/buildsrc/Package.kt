package fe.buildsrc

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileVisitDetails

object Package {
    private const val MERGE_PKG: String = "fe.merged.dependencies"

    private fun FileVisitDetails.toParentDir(): String? {
        if (relativePath.segments[0] != "META-INF") {
            return relativePath.segments.let { it.take(it.size - 1) }.joinToString(".")
        }

        return null
    }

    fun Project.relocatePackages(configuration: Configuration): Map<String, String> {
        val pkgs = mutableMapOf<String, String>()
        configuration.resolvedConfiguration.firstLevelModuleDependencies.forEach { dependency ->
            dependency.moduleArtifacts.forEach { artifact ->
                zipTree(artifact.file).visit {
                    if (!isDirectory) {
                        toParentDir()?.let { pkg ->
                            pkgs[pkg] = "$MERGE_PKG.${dependency.moduleName}.${dependency.moduleVersion}.$pkg"
                        }
                    }
                }
            }
        }

        return pkgs
    }
}
