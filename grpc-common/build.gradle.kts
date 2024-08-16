val grpcVersion = "1.54.0"
val grpcKotlinVersion = "1.4.1"
val protobufVersion = "3.22.0"
val defaultProtocPlatform = "osx-x86_64"
val protocPlatform: String = project.findProperty("protocPlatform") as String? ?: defaultProtocPlatform

plugins {
	id("com.google.protobuf") version "0.9.4"
}

repositories {
	google()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
	implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion:$protocPlatform"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion:$protocPlatform"
		}
		create("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
		}
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				create("grpc")
				create("grpckt")
			}
		}
	}
}