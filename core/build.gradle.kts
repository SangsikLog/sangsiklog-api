import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
	kotlin("plugin.jpa") version "1.9.24"
}

dependencies {
	api("io.github.microutils:kotlin-logging-jvm:2.0.11")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}