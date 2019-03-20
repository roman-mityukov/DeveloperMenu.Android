package com.actonica.devmenu.buildsrc

class Config {
    object Dependency {
        const val ANDROID_GRADLE_PLUGIN: String = "com.android.tools.build:gradle:3.3.0"
        const val KOTLIN_GRADLE_PLUGIN: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11"
        const val BINTRAY_GRADLE_PLUGIN: String =
            "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
        const val FABRIC_GRADLE_PLUGIN: String = "io.fabric.tools:gradle:1.27.0"
        const val KTLINT_GRADLE_PLUGIN: String = "org.jlleitschuh.gradle:ktlint-gradle:7.1.0"
        const val DETEKT_GRADLE_PLUGIN: String = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.0-RC13"

        const val APPCOMPAT: String = "androidx.appcompat:appcompat:1.0.2"
        const val MULTIDEX: String = "com.android.support:multidex:1.0.3"
        const val CONSTRAINT_LAYOUT: String = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val RECYCLERVIEW: String = "androidx.recyclerview:recyclerview:1.0.0"
        const val PLAY_SERVICES_LOCATION: String =
            "com.google.android.gms:play-services-location:16.0.0"
        const val PLAY_SERVICES_MAP: String = "com.google.android.gms:play-services-maps:16.0.0"

        const val KOTLIN: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.11"
        const val KOTLIN_COROUTINES_CORE: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1"
        const val KOTLIN_COROUTINES_ANDROID: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.0.1"

        const val SEISMIC: String = "com.squareup:seismic:1.0.2"
        const val DAGGER: String = "com.google.dagger:dagger:2.15"
        const val DAGGER_COMPILER: String = "com.google.dagger:dagger-compiler:2.15"
        const val DAGGER_ANDROID: String = "com.google.dagger:dagger-android:2.15"
        const val DAGGER_ANDROID_SUPPORT: String = "com.google.dagger:dagger-android-support:2.15"
        const val DAGGER_ANDROID_PROCESSOR: String =
            "com.google.dagger:dagger-android-processor:2.15"
        const val LYNX: String = "com.github.pedrovgs:lynx:1.1.0"
        const val CHUCK: String = "com.readystatesoftware.chuck:library:1.1.0"
        const val CHUCK_NO_OP: String = "com.readystatesoftware.chuck:library-no-op:1.1.0"
        const val OKHTTP: String = "com.squareup.okhttp3:okhttp:3.11.0"
        const val SLF4J: String = "org.slf4j:slf4j-api:1.7.25"
        const val LOG4J2_API: String = "org.apache.logging.log4j:log4j-api:2.3"
        const val LOG4J2_CORE: String = "org.apache.logging.log4j:log4j-core:2.3"
        const val LOG4J2_SLF4J_IMPL: String = "org.apache.logging.log4j:log4j-slf4j-impl:2.3"
        const val LOG4J2_ANDROID: String = "io.rm.log4j2.android:log4j2-android:1.0.0"

        const val JUNIT: String = "junit:junit:4.12"
        const val TEST_CORE: String = "androidx.test:core:1.1.0"
        const val TEST_RUNNER: String = "androidx.test:runner:1.1.0"
        const val TEST_EXTENSIONS: String = "androidx.test.ext:junit:1.0.0"
        const val TEST_RULES: String = "androidx.test:rules:1.1.0"
        const val TEST_ROBOLECTRIC: String = "org.robolectric:robolectric:4.1"
        const val ESPRESSO_CORE: String = "androidx.test.espresso:espresso-core:3.1.0"

        const val CRASHLYTICS: String = "com.crashlytics.sdk.android:crashlytics:2.9.7@aar"
    }

    object Sdk {
        const val MIN_SDK_VERSION: Int = 16
        const val COMPILE_SDK_VERSION: Int = 28
        const val TARGET_SDK_VERSION: Int = 28
    }
}