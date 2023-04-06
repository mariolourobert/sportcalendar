# SportCalendar

SportCalendar is a demo native Android app. It displays a simple sport team calendar.

## Main libraries

SportCalendar is built with well known Kotlin/Android libraries, such as :

- [Compose](https://developer.android.com/jetpack/compose) for the UI layer
- [Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) to handle asynchronous calls
- [Flow](https://kotlinlang.org/docs/flow.html) to expose a reactive state to the UI layer
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (from AndroidX lifecycle)
- [Koin](https://insert-koin.io/) as Dependency Injection library
- [Retrofit](https://square.github.io/retrofit/)
- [KotlinX Serialization](https://kotlinlang.org/docs/serialization.html)
- [JUnit 4](https://junit.org/junit4/) to execute unit tests
- [MockK](https://mockk.io/) to mock dependencies during tests
- [Gradle](https://docs.gradle.org/) is obviously used as build engine, using [Kotlin script .kts](https://developer.android.com/build/migrate-to-kts) files.
- [Version Catalogs](https://developer.android.com/build/migrate-to-catalogs) is set through a nice libs.versions.toml file

## Architecture

SportCalendar is built following a 3-layers clean architecture (close to what is proposed by Google in [NowInAndroid](https://github.com/android/nowinandroid)) :

- a UI layer, with MVVM as presentation pattern
- a Domain layer which handle business logic through UseCases and mappers
- a Data layer with repositories as Single Source Of Truth and API classes

Each layer is put in a proper Gradle module.  
An app module is put in top of everything, with limited responsibilities.

#### Is this architecture overkilled in regards to specs ?
May be ! But this project is a demo app ! So I chose to architecture it like if it was a feature in a bigger project.

## Possible improvements

With more time, I would add some things in the project :

- Add UI tests
- Unit test the ViewModel
- Add Gradle custom plugin to avoid duplicating stuffs on modules gradle files