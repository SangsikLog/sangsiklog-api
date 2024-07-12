import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.1" apply false
	id("io.spring.dependency-management") version "1.1.5" apply false
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24" apply false
}

allprojects {
	group = "com.sangsiklog"
	version = "0.0.1-SNAPSHOT"

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
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")

		implementation ("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.kafka:spring-kafka")
		implementation("org.springframework.boot:spring-boot-starter-integration")
		implementation("org.springframework.integration:spring-integration-core")
		implementation("org.springframework.integration:spring-integration-kafka")
		implementation("org.redisson:redisson:3.32.0")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		runtimeOnly("com.mysql:mysql-connector-j")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.kafka:spring-kafka-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}
}