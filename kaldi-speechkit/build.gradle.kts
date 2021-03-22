import com.justai.gradle.project.configureAndroid
import com.justai.gradle.project.configureProject

plugins {
    id("com.android.library")
}

configureProject {
    isLibrary = true
    createMavenPublication = true
    publishToBintray = true
}

configureAndroid {}

dependencies {
    implementation(project(":core"))
    implementation(Library.Kotlin.stdLib)
    implementation(Library.Android.appCompat)
    implementation(Library.Kotlin.coroutines)

    implementation("com.alphacep:vosk-android:0.3.17")
    implementation("com.neovisionaries:nv-websocket-client:2.9")
}
