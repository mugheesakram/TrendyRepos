import java.util.*

plugins {
    id(BuildPluginsConfig.androidApplication)
    kotlin(BuildPluginsConfig.kotlinAndroid)
    kotlin(BuildPluginsConfig.kotlinKapt)
    id(BuildPluginsConfig.androidHilt)
    id(BuildPluginsConfig.navigationSafeArgs)
    id(BuildPluginsConfig.kotlinParcelize)
}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation

//        val localProperties = File(project.rootDir, "buildSrc/apikeys.properties")
//        val properties = Properties()
//        if (localProperties.exists()) {
//            localProperties.inputStream().use { properties.load(it) }
//            try {
//                if (properties.containsKey("API_KEY")) {
//                    buildConfigField(
//                        "String",
//                        "API_KEY",
//                        properties["API_KEY"].toString()
//                    )
//                }
//                if (properties.containsKey("BASE_URL")) {
//                    buildConfigField(
//                        "String",
//                        "BASE_URL",
//                        properties["BASE_URL"].toString()
//                    )
//                }
//
//            } catch (e: Exception) {
//                System.err.println(e.printStackTrace())
//            }
//        }
    }



    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    buildFeatures {
        this.dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(DependenciesManager.kotlinImplementation)
    implementation(DependenciesManager.networkImplementation)
    implementation(DependenciesManager.lifeCycleKtxImplementation)
    implementation(DependenciesManager.androidxImplementation)
    implementation(DependenciesManager.navigationImplementation)
    implementation(DependenciesManager.thirdPartyImplementation)
    implementation(DependenciesManager.hiltImplementation)
    testImplementation(DependenciesManager.testingImplementation)
    kapt(DependenciesManager.hiltKapt)
    androidTestImplementation(DependenciesManager.androidTestImplementation)
}
