# sbt-clean

![build](https://github.com/kinoplan/sbt-clean/workflows/build/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/io.kinoplan/sbt-clean_2.12_1.0.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=sbt-clean&smo=true&namespace=io.kinoplan)
[![vscode](https://img.shields.io/static/v1?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAADv0lEQVR4nO2ZvWsUQRjGZ97VJogfaSSFQbC5bWyUYGMRsbWwsBObgIWFMWBmLid4gtGZBNTOzn9ABEHZGMzsLRo/Ck935hRUSBELQQIKIrkLEjMyG8479T5mde8L7oEpd+f57b4z88wMQn311VfH5FBxDKhY2sZfPXMfFA+jXhIQMQbUXwfqayctNty5ona94suUVzyFshpQNwuoGAcqNoz5cosAKu21ATmQ11tRd0ljTMRstXGoDRC11Nzqsuutjh+4pwc67RyhbLAFE3GrlnmoA/CrecWV1NzqBNIad8j8vQFMhFfPPDQDqIBk2m8+vbgLiHjSyDzYA3xsr/nMoyGgvmpmHmwB5oq6febP+/vMHG9jHloCYAYMD0cQC/fGNj8ZHAQiVmzNQ+IA194MYq4C4EoDU+uYyWl0+7Zj86gzKUaB+F/jmIdEAfibYeDybWS+qhkgNJ0famieiuNARCmWeSJKkM6dTgwAuFr803ylyU8Ok0dqPjeZOwPE/xHvy4t36LzYb55PDoDJz/UBNksKmLxYnVcgLWjcksFE3EF0YUf5HYkBYC4vNwQolxSTD9GVwm5MxPWY5tdMFvqzXzexMaA1xkxdsoEAJktwYTFOySwjIkZqdesmPY0Cl2eByx/NIZSG7HMNNNe4ZKh/F40HO+v157ZiIQMWngSmvlv9jct5Demg1lf/bsZIs77cVq3EDiscBS6/WUFcDTVkHlUDfEAkOGTTj9vSKMHDEeDyixWEadnnGhP/PpqYH7Ttwm0pABEjkAm+RF/YEgKz8L5Z0TsO4KQXjgIR36KSyAQarryyhgAuPyCuOldCQMXJaBBWD8opA5G3hzCTACu0fxADyZ2tGw3Suc1Zx/pPRAvfXXQjbMc0qjGm/qXmi5KBeFGKAwFMLUcRvZUAmPrTVlmG+g/RlNiNubweD0KuAVOtixJA/c9NzK8DFRdRNlsJc6xAY0FsxvM7iOeTD3NA/cf1s7v/yaF+7Tg9o85YRY/f/8Y7NFtINk4jujAMRLz9O/76gdmkN3rUYeFx4DLeuOCyBKyQ3IYm0sT8oDFcLhkzLtAJyy3lTDgKXH2NW1Ju8uuAxmZzji7M70FxNVM4CFytdBjgPzX7eh8wudS7AEbT+SFgUvUugBEr7AKuniQC4LX7aLGsbH4Ac+n9L0DKK051BiCCCLZgrm79E4AXHa+f69zxevWBAZeztgCprrrgqJLJQsDkRgOAbr1iqgi4HIsOybjSDpe9dclXlsPkMeDq/fabS0977pq1r75Qd+gnbk39qO0MuGMAAAAASUVORK5CYII=\&label=\&message=Open%20in%20Visual%20Studio%20Code\&labelColor=2c2c32\&color=007acc\&logoColor=007acc)](https://vscode.dev/github/kinoplan/sbt-clean)

An sbt plugin that provides various functionality for advanced cleanup of scala projects

## Setup

This sbt plugin is available for sbt 1.x (1.9.0 or later) and sbt 2.x (2.0.0 or later)

To use the latest version of plugins, include the following in your `project/plugins.sbt`:

```scala
addSbtPlugin("io.kinoplan" % "sbt-clean" % ${version})
```

where you need to replace `${version}` with the version you want to use.

## Configuration

* **CleanAllPlugin**:
  * :rocket: *cleanAll*: Task that executes clean all projects in a build
* **CleanTriggerPlugin**:
  * :rocket: *triggeredClean*: Task that executes the project's `clean` task when the configured sources change
  * :gear: *cleanTriggerSources* (**Default: Nil**): Sources used to determine whether clean should be run

## Example

### CleanAllPlugin

Start by enabling the plugin in your `build.sbt` file or for specific projects:

```scala
enablePlugins(CleanAllPlugin)
```
after that, to start clearing all projects, run the command in sbt shell:

```scala
cleanAll
```

### CleanTriggerPlugin

Start by enabling the plugin for specific project:

```scala
enablePlugins(CleanTriggerPlugin)
```
Before compilation, the plugin runs the project's standard `clean` task if any tracked
files have changed. Directories are checked recursively, and the first run with
tracked files also triggers cleanup.

All standard cleanup settings and custom `clean` actions apply. Set
`cleanTriggerSources := Nil` to disable this behavior.

Configure the sources to track, for example:

```scala
lazy val shared = project
  .in(file("shared"))
  .settings(
    ...
  )

lazy val server = project
  .in(file("server"))
  .enablePlugins(CleanTriggerPlugin)
  .settings(
    cleanTriggerSources := Seq(
      (ThisBuild / baseDirectory).value / "shared" / "src" / "main" / "scala" / "api" / "internal",
      (ThisBuild / baseDirectory).value / "shared" / "src" / "main" / "scala" / "api" / "external" / "FooApi.scala"
    )
  )
```

## Contributing

See [CONTRIBUTING.md](/CONTRIBUTING.md) for more details about how to contribute.

## License

This project is licensed under the terms of the [Apache License, Version 2.0](/LICENSE).
