import net.neoforged.moddevgradle.legacyforge.dsl.MixinExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.slf4j.event.Level

plugins {
    idea
    eclipse
    `maven-publish`
    kotlin("jvm") version "2.4.0"
    id("net.neoforged.moddev.legacyforge") version "2.0.91"
    id("com.diffplug.spotless") version "7.0.2"
}

val mod_version: String by project
val maven_group: String by project
val archives_base_name: String by project
val minecraft_version: String by project
val forge_version: String by project
val mapping_version: String by project
val mod_id: String by project
val mod_license: String by project
val mod_name: String by project
val mod_url: String by project
val mod_author: String by project
val gtceu_version: String by project
val ldlib_version: String by project
val registrate_version: String by project
val configuration_version: String by project
val jei_version: String by project
val emi_version: String by project
val kotlinforforge_version: String by project

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "GTCEu Maven"
        url = uri("https://maven.gtceu.com")
        content {
            includeGroup("com.gregtechceu.gtceu")
        }
    }
    maven {
        name = "FirstDarkDev"
        url = uri("https://maven.firstdark.dev/snapshots/")
    }
    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            // scoped narrowly: this host also serves other groups whose versions overlap ours
            includeGroup("com.tterrag.registrate")
        }
    }
    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        url = uri("https://maven.theillusivec4.top/")
    }
    maven {
        url = uri("https://cursemaven.com/")
        content {
            includeGroup("curse.maven")
        }
    }
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
    maven {
        // required now that the mod's sources are Kotlin
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

version = mod_version
group = maven_group

base {
    archivesName.set(archives_base_name)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

legacyForge {
    version = "$minecraft_version-$forge_version"

    parchment {
        mappingsVersion = mapping_version
        minecraftVersion = minecraft_version
    }

    runs {
        configureEach {
            // other markers: SCAN (mod scanning), REGISTRYDUMP (full registry contents)
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = Level.DEBUG
        }
        create("client") {
            client()
            sourceSet = sourceSets.main.get()
            programArguments.addAll("--refresh-dependencies")
            systemProperty("forge.enabledGameTestNamespaces", mod_id)
            programArguments.addAll("--username", "Thornite", "--uuid", "7d6cd0a0-6a50-444f-b4df-6a3c8ced5bf7")
        }
        create("server") {
            server()
            sourceSet = sourceSets.main.get()
            systemProperty("forge.enabledGameTestNamespaces", mod_id)
            programArguments.addAll("--nogui", "--world", "world-extra")
        }
        create("data") {
            data()
            sourceSet = sourceSets.main.get()
            programArguments.addAll(
                "--mod",
                mod_id,
                "--all",
                "--output",
                file("src/generated/resources/").absolutePath,
                "--existing",
                file("src/main/resources/").absolutePath
            )
            programArguments.addAll("--existing-mod", "gtceu")
        }
    }

    mods {
        create(mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.jar {
    manifest.attributes(
        "MixinConfigs" to "flatcore.mixins.json",
    )
}

apply(from = "$rootDir/gradle/scripts/spotless.gradle")

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.1")
    implementation("thedarkcolour:kotlinforforge:$kotlinforforge_version")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")

    "modCompileOnly"("mezz.jei:jei-$minecraft_version-forge-api:$jei_version")
    "modCompileOnly"("mezz.jei:jei-$minecraft_version-common-api:$jei_version")
    "modRuntimeOnly"("mezz.jei:jei-$minecraft_version-forge:$jei_version")
    "modRuntimeOnly"("dev.emi:emi-forge:$emi_version+$minecraft_version")
    "modRuntimeOnly"("curse.maven:jade-324717:5390389")

    "modImplementation"("com.gregtechceu.gtceu:gtceu-$minecraft_version:$gtceu_version:slim") { isTransitive = false }
    "modImplementation"("com.lowdragmc.ldlib:ldlib-forge-$minecraft_version:$ldlib_version") { isTransitive = false }
    "modImplementation"("com.tterrag.registrate:Registrate:$registrate_version")
    "modImplementation"("dev.toma.configuration:configuration-forge-$minecraft_version:$configuration_version")

    "modRuntimeOnly"("curse.maven:itemzoom-261725:5043628")
    "modRuntimeOnly"("curse.maven:model-gap-fix-676136:4607206")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

configure<MixinExtension> {
    add(sourceSets.main.get(), "mixins.$mod_id.refmap.json")
    config("$mod_id.mixins.json")
}

tasks.named<ProcessResources>("processResources") {
    val properties = mapOf(
        "mod_license" to mod_license,
        "mod_id" to mod_id,
        "version" to version,
        "mod_name" to mod_name,
        "mod_url" to mod_url,
        "mod_author" to mod_author,
        "forge_version" to forge_version.split(".")[0], // mods.toml wants only the major version
        "minecraft_version" to minecraft_version,
        "gtceu_version" to gtceu_version,
    )
    inputs.properties(properties)

    filesMatching("META-INF/mods.toml") {
        expand(properties + ("project" to project))
    }
}

tasks.named<Jar>("jar") {
    finalizedBy("reobfJar")
}

tasks.withType<JavaCompile>().configureEach {
    // pin encoding regardless of the host system default, which otherwise mangles special characters
    options.encoding = "UTF-8"
    options.release.set(17)
    options.compilerArgs.add("-Aquiet=true") // suppresses Mixin's informational notes
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
