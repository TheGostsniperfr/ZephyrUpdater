plugins {
    id("java")
}

group = "org.thegostsniper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.reflections:reflections:0.9.12")

}

tasks.test {
    useJUnitPlatform()
}