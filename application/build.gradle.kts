tasks.getByName<Jar>("jar") {
    this.enabled = true
}

dependencies{
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}