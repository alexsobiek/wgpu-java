import com.github.monosoul.yadegrap.DelombokTask

plugins {
    id("com.github.johnrengelman.shadow")
    id("com.github.monosoul.yadegrap")
    java
}

repositories {
    mavenCentral()
    maven("https://maven.alexsobiek.com/snapshosts")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks {
    jar {
        archiveClassifier.set("unshaded")
    }

    build {
        dependsOn(shadowJar)
    }


    shadowJar {
        archiveClassifier.set("")
    }

    val delombok = "delombok"(DelombokTask::class)

    "javadoc"(Javadoc::class) {
        dependsOn(delombok)
        setSource(delombok)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
