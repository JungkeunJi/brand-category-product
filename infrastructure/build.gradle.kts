tasks.getByName<Jar>("jar") {
    this.enabled = true
}

dependencies {
    implementation(project(":domain"))

    runtimeOnly("com.h2database:h2")
}