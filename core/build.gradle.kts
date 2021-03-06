import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("kotlinx-serialization")
}

group = "net.subroh0508.otonashi"
version = Packages.versionName



java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compile(project(":triples"))
    compile(Dep.Kotlin.stdlibJdk8)
    compile(Dep.Kotlin.serialization)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlin.Experimental"
        )
    }
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi", Packages.versionName)
