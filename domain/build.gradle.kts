tasks.getByName<Jar>("jar") {
    this.enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}