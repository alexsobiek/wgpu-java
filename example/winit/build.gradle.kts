plugins {
    id("com.alexsobiek.wgpu.java-application-conventions")
}

application {
    mainClass.set("com.alexsobiek.wgpu.example.winit.WinitExample")
}

dependencies {
    implementation(project(":java"))
}