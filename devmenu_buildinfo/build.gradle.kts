import com.actonica.devmenu.buildsrc.Config

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Config.Sdk.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdkVersion(Config.Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Config.Sdk.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":devmenu_contract"))
    implementation(Config.Dependency.KOTLIN)
    implementation(Config.Dependency.APPCOMPAT)
    implementation(Config.Dependency.CONSTRAINT_LAYOUT)

    testImplementation(Config.Dependency.JUNIT)
    testImplementation(Config.Dependency.TEST_CORE)
    testImplementation(Config.Dependency.TEST_ROBOLECTRIC)

    androidTestImplementation(Config.Dependency.TEST_RUNNER)
    androidTestImplementation(Config.Dependency.ESPRESSO_CORE)
}

this.ext.apply {
    set("moduleName", "Developer Menu BuildInfo")
    set("moduleArtifactId", "devmenu_buildinfo")
    set("moduleDescription", "Developer Menu BuildInfo")
}

apply {
    from("../publish.gradle")
}