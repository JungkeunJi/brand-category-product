tasks.getByName<Jar>("jar") {
    this.enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}