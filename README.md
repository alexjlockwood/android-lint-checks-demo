# android-lint-checks-demo

This is a simple demo project that shows how to configure and write custom lint checks in an Android project.

## Custom lint checks

This project contains the below custom lint checks. You can see them show up as errors if you open this project in Android Studio and look at the [`MainActivity.kt`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/src/main/java/com/lyft/android/app/MainActivity.kt) and [`activity_main.xml`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/src/main/res/layout/activity_main.xml) files.

* [`DeprecatedButtonLayoutXmlDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedButtonLayoutXmlDetector.kt) - Prohibits usages of a hypothetical `DeprecatedButton` class in layout XML resource files. 

* [`DeprecatedButtonJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedButtonJavaKotlinDetector.kt) - Prohibits instantiations of a hypothetical `DeprecatedButton` class in Java and Kotlin code.

* [`DeprecatedRedResourceLayoutXmlDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedRedResourceLayoutXmlDetector.kt) - Prohibits references to a hypothetical `@color/deprecated_red` resource in layout XML resource files.

* [`DeprecatedRedResourceJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedRedResourceJavaKotlinDetector.kt) - Prohibits references to a hypothetical `R.color.deprecated_red` resource in Java and Kotlin code.

* [`AndroidToastJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/AndroidToastJavaKotlinDetector.kt) - Prohibits usage of the Android `Toast` class in Java and Kotlin code and suggests using a `Snackbar` from the support library instead.

## Project setup

This project contains the following two modules:

### Lint check jar library (`checks/`)

This is the lint check jar library that other modules consume. This module contains the custom lint check implementations, each of which are listed in the [`IssueRegistry`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/IssueRegistry.kt) class. Unit tests are written for each custom lint check implementation.

### Android app module (`app/`)

This module contains a generic sample app. In order to get the custom lint checks running on the code in this module, we depend on the `checks` module above in the [`app/build.gradle`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/build.gradle#L29) file:

```
dependencies {
    lintChecks project(':checks')
}
```

## Additional resources

* [KotlinConf 2017 - Kotlin Static Analysis with Android Lint](https://www.youtube.com/watch?v=p8yX5-lPS6o) - An incredible 40 minute deep-dive into lint. I highly recommend watching this video.
* [Getting the Most Out of Android Lint (Android Dev Summit '18)](https://www.youtube.com/watch?v=ffH-LD5uP4s) - A shorter video about Android lint, but also very informative.
* [Source code for the Android Studio lint checks](https://android.googlesource.com/platform/tools/base/+/mirror-goog-studio-master-dev/lint/libs/lint-checks/src/main/java/com/android/tools/lint/checks) - In my opinion this is the best source of information when it comes to writing custom lint checks. You can learn a lot about how to write them by analyzing this source code.
* [Sample project with custom lint checks](https://github.com/googlesamples/android-custom-lint-rules) - Another example repository with more detailed comments about the project's setup from Tor Norbye himself.
* [Lint mailing list](https://groups.google.com/forum/#!forum/lint-dev) - A great place to asking questions about anything related to lint.
