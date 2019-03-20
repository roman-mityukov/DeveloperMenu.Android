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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":devmenu_contract"))
    implementation(Config.Dependency.KOTLIN)
    implementation(Config.Dependency.APPCOMPAT)
    implementation(Config.Dependency.CONSTRAINT_LAYOUT)

    implementation(Config.Dependency.SEISMIC)
    implementation(Config.Dependency.RECYCLERVIEW)

    testImplementation(Config.Dependency.JUNIT)
    androidTestImplementation(Config.Dependency.TEST_RUNNER)
    androidTestImplementation(Config.Dependency.ESPRESSO_CORE)
}

this.ext.apply {
    set("moduleName", "Developer Menu")
    set("moduleArtifactId", "devmenu")
    set("moduleDescription", "Developer Menu")
}


apply {
    from("../publish.gradle")
}