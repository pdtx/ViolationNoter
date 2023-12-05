plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.yaki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies{
    // https://mvnrepository.com/artifact/com.alibaba/fastjson
    implementation("com.alibaba:fastjson:2.0.32")

    // https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2
//    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")

    // https://mvnrepository.com/artifact/commons-httpclient/commons-httpclient
//    implementation("commons-httpclient:commons-httpclient:3.1")
//    implementation("net.sf.json-lib:json-lib:2.4")

    // https://mvnrepository.com/artifact/org.springframework/spring-web
//    implementation("org.springframework:spring-web:6.0.6")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20230227")

    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("Git4Idea"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

// 设置编码为 UTF-8
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
