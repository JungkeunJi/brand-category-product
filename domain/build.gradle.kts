tasks.getByName<Jar>("jar") {
    this.enabled = true
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}