import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

val grpcVersion = "1.54.0"
val grpcKotlinVersion = "1.4.1"
val protobufVersion = "3.22.0"

bootJar.enabled = false
jar.enabled = true

plugins {
	kotlin("plugin.jpa") version "1.9.24"
}

dependencies {
	api("io.github.microutils:kotlin-logging-jvm:2.0.11")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
	implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("io.grpc:grpc-services:$grpcVersion")
	implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
