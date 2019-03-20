import com.actonica.devmenu.buildsrc.Config
import com.actonica.devmenu.buildsrc.VersionProvider
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("io.fabric")
}

android {
    compileSdkVersion(Config.Sdk.COMPILE_SDK_VERSION)

    val code = if (project.hasProperty("buildNumber")) {
        Integer.parseInt(project.property("buildNumber") as String)
    } else {
        VersionProvider.getVersion()
    }

    val majorVersion = "1"
    val minorVersion = "0"
    val appVersion = "$majorVersion.$minorVersion.$code"

    defaultConfig {
        minSdkVersion(Config.Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Config.Sdk.TARGET_SDK_VERSION)
        applicationId = "com.actonica.devmenusample"
        versionCode = code
        versionName = appVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    androidExtensions {
        configure(delegateClosureOf<AndroidExtensionsExtension> {
            isExperimental = true
        })
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
    }

    lintOptions {
        isWarningsAsErrors = true
        setLintConfig(File("lint.xml"))
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":devmenu"))
    implementation(project(":devmenu_contract"))
    implementation(project(":devmenu_baseurl"))
    implementation(project(":devmenu_buildinfo"))
    implementation(project(":devmenu_deviceinfo"))
    implementation(project(":devmenu_location"))
    implementation(project(":devmenu_logger"))
    implementation(project(":devmenu_logcat"))
    implementation(project(":devmenu_http"))
    implementation(project(":devmenu_settings"))

    implementation(Config.Dependency.KOTLIN)
    implementation(Config.Dependency.MULTIDEX)
    implementation(Config.Dependency.APPCOMPAT)
    implementation(Config.Dependency.CONSTRAINT_LAYOUT)
    implementation(Config.Dependency.RECYCLERVIEW)

    implementation(Config.Dependency.DAGGER)
    kapt(Config.Dependency.DAGGER_COMPILER)
    implementation(Config.Dependency.DAGGER_ANDROID)
    implementation(Config.Dependency.DAGGER_ANDROID_SUPPORT)
    kapt(Config.Dependency.DAGGER_ANDROID_PROCESSOR)
    implementation(Config.Dependency.OKHTTP)
    implementation(Config.Dependency.KOTLIN_COROUTINES_CORE)
    implementation(Config.Dependency.KOTLIN_COROUTINES_ANDROID)

    debugImplementation(Config.Dependency.CHUCK)
    releaseImplementation(Config.Dependency.CHUCK_NO_OP)

    implementation(Config.Dependency.SLF4J)
    implementation(Config.Dependency.LOG4J2_SLF4J_IMPL)

    testImplementation(Config.Dependency.JUNIT)
    androidTestImplementation(Config.Dependency.TEST_RUNNER)
    androidTestImplementation(Config.Dependency.ESPRESSO_CORE)

    implementation(Config.Dependency.CRASHLYTICS) {
        isTransitive = true
    }
}