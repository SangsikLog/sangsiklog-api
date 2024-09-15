import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks

bootJar.enabled = true

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.graphql-java:graphql-java-extended-scalars:22.0")

    implementation(project(":grpc-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0")

    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")

    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-core")

    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.springframework.graphql:spring-graphql-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
