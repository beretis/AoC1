plugins {
    kotlin("jvm") version "1.9.20"

}

repositories {
    mavenCentral() // or jcenter() if you prefer
}

dependencies {
    // Make sure to use the latest version of kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
