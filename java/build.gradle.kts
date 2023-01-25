plugins {
    id("com.alexsobiek.wgpu.java-library-conventions")
    id("fr.stardustenterprises.rust.importer") version "3.2.5"
}

dependencies {
    rust(project(":rust"))
}

rustImport {
    baseDir.set("/META-INF/natives")
    layout.set("hierarchical")
}