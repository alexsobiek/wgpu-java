rootProject.name = "wgpu-java"
include("rust", "java")

example("winit")


fun example(name: String) {
    setupExample("example-$name") {
        projectDir = file("example/${name}")
    }
}

inline fun setupExample(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}