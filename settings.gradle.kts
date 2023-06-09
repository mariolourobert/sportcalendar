pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SportCalendar"
include(
    ":app",
    ":calendarfeature",
    ":calendarrepository",
    ":commontools",
    ":designsystem",
    ":getcalendarusecase",
    ":network",
    ":testtools",
)
