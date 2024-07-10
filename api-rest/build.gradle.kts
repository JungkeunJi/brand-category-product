import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<BootJar> {
    this.enabled = true
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

