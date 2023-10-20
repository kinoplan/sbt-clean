# sbt-clean

![build](https://github.com/kinoplan/sbt-clean/workflows/build/badge.svg)
[![Mergify Status](https://img.shields.io/endpoint.svg?url=https://api.mergify.com/v1/badges/kinoplan/utils?style=flat)](https://mergify.com)
[![Maven Central](https://img.shields.io/maven-central/v/io.kinoplan/sbt-clean.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.kinoplan%22%20AND%20a:%22sbt-clean22)
[![vscode](https://img.shields.io/static/v1?logo=visualstudiocode&label=&message=Open%20in%20Visual%20Studio%20Code&labelColor=2c2c32&color=007acc&logoColor=007acc)](https://open.vscode.dev/kinoplan/sbt-clean)


An sbt plugin provides various functionality for advanced cleanup of scala projects

## Setup

This sbt plugin is available for sbt 1.x

To use the latest version of plugins, include the following in your `project/plugins.sbt`:

```scala
addSbtPlugin("io.kinoplan" % "sbt-clean" % ${version})
```

where you need to replace `${version}` with the version you want to use.

## Configuration

* **CleanAllPlugin**:
  * :rocket: *cleanAll*: Task that executes clean all projects in a build
* **CleanTriggerPlugin**:
  * :rocket: *triggeredClean*: Task that executes the clean task based on whether the sources is changed
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
then configure the sources whose changes will trigger the command to clean the `/target` folder and `compile` the project, for example:

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
