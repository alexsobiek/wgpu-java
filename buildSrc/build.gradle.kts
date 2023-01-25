plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    implementation("gradle.plugin.com.github.monosoul:yadegrap:1.0.0")
}
