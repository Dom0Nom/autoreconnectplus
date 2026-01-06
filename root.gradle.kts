plugins {
    kotlin("jvm") version("1.9.10") apply(false)
    id("dev.deftu.gradle.multiversion-root") version("2.59.+")
}

preprocess {
    val forge18909 = createNode("1.8.9-forge", 10809, "srg")
    forge18909.link(forge18909)
}
