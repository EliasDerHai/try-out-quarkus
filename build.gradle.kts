plugins {
    java
    id("io.quarkus")
    id("nu.studer.jooq") version "8.2"
    id("org.flywaydb.flyway") version "11.1.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    // quarkus
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-config-yaml")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")

    // vavr
    implementation("io.vavr:vavr:0.10.5")
    compileOnly("io.vavr:vavr-jackson:0.10.3")
    testImplementation("io.vavr:vavr:0.10.5")
    testImplementation("io.vavr:vavr-jackson:0.10.3")

    // db
    implementation("io.quarkus:quarkus-flyway:11.1.0")
    implementation("io.quarkus:quarkus-jdbc-postgresql:11.1.0")
    implementation("org.flywaydb:flyway-database-postgresql:11.1.0")
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
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
    options.setIncremental(true)
}

flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/try_out_jooq"
    user = "postgres"
    password = "postgres"
}

jooq {
    version.set("3.18.5")
    configurations {
        create("main") {

            jooqConfiguration.apply {
                // options = [DEBUG, INFO, WARN, ERROR]
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
                        inputSchema = "public"
                    }
                    target.apply {
                        packageName = "com.elija.generated"
                    }
                }
            }
        }
    }
}

tasks.named("generateJooq").configure {
    dependsOn("flywayMigrate")
}

/*
 * https://github.com/flyway/flyway/issues/3774#issuecomment-1829245496
 * took me ages. what a piece of junk...
 */
buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.1.0")
    }
}


/**
 * Trying to get a fast unit-test task going, but it seems just impossible without introducing a subproject without
 * quarkus. The unit-test task should build in ~5s, which is not great but seems to be the best I can do for now.
 */
tasks.register<Test>("unitTest") {
    description = "Runs only the fast unit tests (no Quarkus, Flyway, jOOQ, etc.)"
    group = "verification"
    useJUnitPlatform()

    // Explicitly set the test classes and classpath to use the existing 'test' source set.
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    exclude("**/*IT.class")

    doFirst {
        println("Running fast unit tests only (skip Quarkus/Flyway/jOOQ) ...")
    }
}

listOf(
        "processResources",
        "quarkusGenerateAppModel",
        "quarkusGenerateDevAppModel",
        "quarkusGenerateCodeDev",
        "quarkusGenerateCode",
        "flywayMigrate",
        "generateJooq",
        "compileQuarkusTestGeneratedSourcesJava",
        "quarkusGenerateTestAppModel",
        "quarkusGenerateCodeTests",
        "processTestResources"
).forEach { t ->
    tasks.named(t).configure {
        onlyIf {
            !gradle.startParameter.taskNames.contains("unitTest")
        }
    }
}