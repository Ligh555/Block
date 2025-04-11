pluginManagement {
    repositories {
        mavenLocal()
        maven {
            setUrl("https://maven.aliyun.com/repository/google")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/public")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/jcenter")
        }
        maven { url = uri("https://repo.huaweicloud.com/repository/maven") }
        maven {
            setUrl("https://maven.aliyun.com/repository/gradle-plugin")
        }
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        maven {
            setUrl("https://maven.aliyun.com/repository/google")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/public")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/jcenter")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/gradle-plugin")
        }
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}

rootProject.name = "Blog"
include(":app")
include(":android")
include(":JavaTest")
include(":base")
include(":leakcanary")
include(":ipc")
include(":biometric")
include(":wheelView")
include(":chart")
