import io.gitlab.arturbosch.detekt.detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://maven.fabric.io/public")
        }
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.ANDROID_GRADLE_PLUGIN)
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.KOTLIN_GRADLE_PLUGIN)
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.BINTRAY_GRADLE_PLUGIN)
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.FABRIC_GRADLE_PLUGIN)
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.KTLINT_GRADLE_PLUGIN)
        classpath(com.actonica.devmenu.buildsrc.Config.Dependency.DETEKT_GRADLE_PLUGIN)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        maven {
            setUrl("https://dl.bintray.com/actonica/android")
        }
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config = files("../default-detekt-config.yml")
    }
}