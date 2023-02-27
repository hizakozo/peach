import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.rohanprabhu.gradle.plugins.kdjooq.*

val springVersion = "2.7.8"
plugins {
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.6"
	id("org.liquibase.gradle") version "2.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-security:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-jooq:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-redis:$springVersion")
	// https://mvnrepository.com/artifact/com.google.auth/google-auth-library-oauth2-http
	implementation("com.google.auth:google-auth-library-oauth2-http:1.15.0")
	implementation("com.auth0:java-jwt:4.0.0")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	implementation("io.arrow-kt:arrow-core:1.0.1")
	implementation("io.arrow-kt:arrow-fx-coroutines:1.0.1")
	implementation("io.arrow-kt:arrow-fx-stm:1.0.1")
	implementation("commons-validator:commons-validator:1.7")

	implementation("com.google.firebase:firebase-admin:9.1.1")

	implementation("org.liquibase:liquibase-core:4.19.0")
	implementation("org.liquibase:liquibase-groovy-dsl:3.0.2")
	liquibaseRuntime("org.postgresql:postgresql:42.3.1")
	liquibaseRuntime("org.liquibase:liquibase-core:4.19.0")
	liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.2")
	liquibaseRuntime("info.picocli:picocli:4.6.1")

	implementation("com.google.api-client:google-api-client-gson:1.33.0")
	jooqGeneratorRuntime("org.postgresql:postgresql:42.3.1")
}

liquibase {
	activities.register("main") {
		val db_url = System.getenv("DB_URL") ?: "localhost:5454/peach"
		val db_user = System.getenv("DB_USER") ?: "docker"
		val db_password = System.getenv("DB_PASS") ?: "docker"

		this.arguments = mapOf(
			"logLevel" to "info",
			"changeLogFile" to "src/main/resources/liquibase/xml/db.changelog.xml",
			"url" to "jdbc:postgresql://$db_url",
			"username" to db_user,
			"password" to db_password,
		)
	}
	runList = "main"
}

jooqGenerator {
	jooqEdition = JooqEdition.OpenSource
	jooqVersion = "3.14.0"
	attachToCompileJava = false
	configuration("primary", project.the<SourceSetContainer>()["main"]) {
		configuration = jooqCodegenConfiguration {
			jdbc {
				username = "docker"
				password = "docker"
				driver   = "org.postgresql.Driver"
				url      = "jdbc:postgresql://localhost:5454/peach"
			}

			generator {
				target {
					packageName = "com.example.peachapi.driver.peachdb.gen"
					directory   = "${project.rootDir}/src/main/kotlin"
				}

				database {
					name = "org.jooq.meta.postgres.PostgresDatabase"
					inputSchema = "public"
				}
			}
		}
	}
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
