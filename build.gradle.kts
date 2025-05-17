plugins {
    id("java")
}

group = "com.group16.stardewvalley"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
//    implementation("com.google.code.gson:gson:2.10.1")

    // Jackson core and databind for JSON handling
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation ("com.fasterxml.jackson.core:jackson-core:2.17.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.17.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
