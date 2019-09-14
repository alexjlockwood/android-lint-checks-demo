# android-lint-checks-demo

This is a simple demo project that shows how to configure lint in an Android project.

## Project setup

This project contains the following two modules:

### Lint check jar library (`checks/`)

This is the lint check jar library that other modules consume. This module contains
the custom lint check implementations, each of which are listed in the `IssueRegistry`
class.

### Example Android app module (`app/`)

This module contains a generic sample app. In order to get the custom lint checks
running on the code in this module, we depend on the `checks` module above in the
`app/build.gradle` file:

```
dependencies {
    lintChecks project(':checks')
}
```

## Additional resources

* [KotlinConf 2017 - Kotlin Static Analysis with Android Lint](https://www.youtube.com/watch?v=p8yX5-lPS6o)
* [Getting the Most Out of Android Lint (Android Dev Summit '18)](https://www.youtube.com/watch?v=ffH-LD5uP4s)
* [Another sample project containing sample lint checks](https://github.com/googlesamples/android-custom-lint-rules)
* [Lint mailing list](https://groups.google.com/forum/#!forum/lint-dev)
