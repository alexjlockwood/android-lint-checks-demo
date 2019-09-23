# android-lint-checks-demo

This is a simple demo repo that shows how to configure and write custom lint checks in an Android project.

## Custom lint checks

Custom lint checks are a great way to prohibit usages of certain classes and resources in a codebase. To demonstrate their power, this project contains the following custom lint checks:

* [`DeprecatedButtonXmlDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedButtonXmlDetector.kt) - Prohibits usages of a hypothetical `DeprecatedButton` class in layout XML resource files.
* [`DeprecatedButtonJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedButtonJavaKotlinDetector.kt) - Prohibits instantiations of a hypothetical `DeprecatedButton` class in Java and Kotlin code.
* [`DeprecatedPurpleColorXmlDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedPurpleColorXmlDetector.kt) - Prohibits references to a hypothetical `@color/deprecated_purple` resource in layout XML resource files.
* [`DeprecatedPurpleColorJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/DeprecatedPurpleColorJavaKotlinDetector.kt) - Prohibits references to a hypothetical `R.color.deprecated_purple` resource in Java and Kotlin code.
* [`AndroidToastJavaKotlinDetector`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/AndroidToastJavaKotlinDetector.kt) - Prohibits usage of the Android `Toast` class in Java and Kotlin code and suggests using a `Snackbar` from the support library instead.

You can see the lint checks show up as errors if you open this project in Android Studio and look at the [`MainActivity.kt`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/src/main/java/com/lyft/android/app/MainActivity.kt) and [`activity_main.xml`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/src/main/res/layout/activity_main.xml) files.

You can view the unit tests for each custom lint check [here](https://github.com/alexjlockwood/android-lint-checks-demo/tree/master/checks/src/test/java/com/lyft/android/lint/checks).

## Project setup

This project contains the following two modules:

### Lint check jar library (`checks/`)

This module is the lint check jar library that other Android library modules can consume. It contains the custom lint check implementations listed above, each of which are listed in the [`IssueRegistry`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/checks/src/main/java/com/lyft/android/lint/checks/IssueRegistry.kt) class.

### Android app module (`app/`)

This module contains a generic sample Android app. In order to get the custom lint checks running on the code in this module, it depends on the `checks` module in the [`app/build.gradle`](https://github.com/alexjlockwood/android-lint-checks-demo/blob/master/app/build.gradle#L29) file:

```
dependencies {
    lintChecks project(':checks')
}
```

## Additional resources

* [KotlinConf 2017 - Kotlin Static Analysis with Android Lint](https://www.youtube.com/watch?v=p8yX5-lPS6o) - An incredible 40 minute deep-dive into lint. I highly recommend watching this video.
* [Getting the Most Out of Android Lint (Android Dev Summit '18)](https://www.youtube.com/watch?v=ffH-LD5uP4s) - A shorter video about Android lint, but also very informative.
* [Source code for the Android Studio lint checks](https://android.googlesource.com/platform/tools/base/+/mirror-goog-studio-master-dev/lint/libs/lint-checks/src/main/java/com/android/tools/lint/checks) - In my opinion this is the best source of information when it comes to writing custom lint checks. You can learn a lot about how to write them by analyzing this source code.
* [Lint mailing list](https://groups.google.com/forum/#!forum/lint-dev) - A great place to asking questions about anything related to lint.
