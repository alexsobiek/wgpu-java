plugins {
    id("fr.stardustenterprises.rust.wrapper") version "3.2.5"
}

rust {

    release.set(true)

    command.set("cargo")

    cargoInstallTargets.set(true)

    targets {

        this += defaultTarget()

//        create("linux-x86_64") {
//            target = "x86_64-unknown-linux-gnu"
//            outputName = "wgpu_native.so"
//        }
    }
}