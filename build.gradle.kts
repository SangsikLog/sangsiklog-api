import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.1" apply false
	id("io.spring.dependency-management") version "1.1.5" apply false
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24" apply false
	kotlin("kapt") version "1.9.24"
}

allprojects {
	group = "com.sangsiklog"
	version = "0.0.1-SNAPSHOT"

	extra["springCloudVersion"] = "2023.0.3"

	repositories {
		mavenCentral()
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.kapt")

	if (project.name != "grpc-common") {
		apply(plugin = "org.springframework.boot")
		apply(plugin = "io.spring.dependency-management")
		apply(plugin = "org.jetbrains.kotlin.plugin.spring")

		dependencies {
			implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
			implementation("org.jetbrains.kotlin:kotlin-reflect")

			implementation ("org.springframework.boot:spring-boot-starter-web")
			implementation("org.springframework.boot:spring-boot-starter-actuator")
			implementation("org.springframework.boot:spring-boot-starter-cache")
			implementation("org.springframework.boot:spring-boot-starter-data-redis")
			implementation("org.springframework:spring-expression")
			implementation("org.springframework.kafka:spring-kafka")
			implementation("org.springframework.boot:spring-boot-starter-integration")
			implementation("org.springframework.integration:spring-integration-core")
			implementation("org.springframework.integration:spring-integration-kafka")
			implementation("org.redisson:redisson:3.32.0")
			implementation("org.reflections:reflections:0.10.2")

			testImplementation("org.springframework.boot:spring-boot-starter-test")
			testImplementation("org.springframework.kafka:spring-kafka-test")
			testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
			testRuntimeOnly("org.junit.platform:junit-platform-launcher")
		}
	}
}