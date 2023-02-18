buildscript {

    val kotlinVersion = "1.8.0"
    val sqlDelightVersion: String by project

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")
        classpath("com.squareup.sqldelight:gradle-plugin:${sqlDelightVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }

}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.1").apply(false)
    id("com.android.library").version("7.4.1").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
