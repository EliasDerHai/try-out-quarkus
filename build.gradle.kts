plugins {
    java
    id("io.quarkus")
    id("nu.studer.jooq") version "8.2"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    // vavr
    implementation("io.vavr:vavr:0.10.5")
    compileOnly("io.vavr:vavr-jackson:0.10.3")

    // db
    jooqGenerator("org.postgresql:postgresql:42.7.2")
    runtimeOnly("org.postgresql:postgresql:42.7.2")
    implementation("io.quarkiverse.jooq:quarkus-jooq:2.0.1")
}

group = "com.elija"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

jooq {
    version.set("3.18.5")
    configurations {
        create("main") {
            // Kotlin DSL requires using jooqConfiguration.apply { ... }
            jooqConfiguration.apply {
                // Optional logging level (DEBUG, INFO, WARN, ERROR)
                // logging = org.jooq.meta.jaxb.Logging.INFO

                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/try_out_jooq"
                    user = "postgres"
                    password = "postgres"
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        // The schema from which to generate the code
                        inputSchema = "public"
                    }
                    target.apply {
                        // Java package for generated code
                        packageName = "com.example.generated"
                        // Directory for generated sources
                        directory = "${layout.buildDirectory}/generated-src/jooq/main"
                    }
                }
            }
        }
    }
}