import org.typelevel.scalacoptions.ScalacOptions

name := "sbt-clean"
description := "A set of plugins for sbt on advanced clean management"

enablePlugins(SbtPlugin)

addSbtPlugin("com.github.sbt" % "sbt2-compat" % "0.2.0")

crossScalaVersions := Seq("2.12.21", "3.8.4")

pluginCrossBuild / sbtVersion := (
  scalaBinaryVersion.value match {
    case "2.12" => "1.9.0"
    case _      => "2.0.0"
  }
)

// Keep the minimum supported sbt for compilation, but test on the latest sbt 1.
scriptedSbt := (
  scalaBinaryVersion.value match {
    case "2.12" => "1.13.0"
    case _      => "2.0.11"
  }
)

scalacOptions ++= (
  scalaBinaryVersion.value match {
    case "2.12" => Seq("-release", "8")
    case _      => Nil
  }
)

scriptedLaunchOpts :=
  scriptedLaunchOpts.value ++ Seq("-Xmx1024M", "-Dplugin.version=" + version.value)

scriptedBufferLog := false

tpolecatExcludeOptions :=
  Set(
    ScalacOptions.fatalWarnings,
    ScalacOptions.warnError,
    ScalacOptions.warnValueDiscard,
    ScalacOptions.privateWarnValueDiscard,
    ScalacOptions.warnDeadCode,
    ScalacOptions.privateWarnDeadCode,
    ScalacOptions.lintInferAny
  )

Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement


// format: off
inThisBuild(
  List(
    versionScheme := Some(VersionScheme.EarlySemVer),
    organization := "io.kinoplan",
    homepage := Some(uri("https://github.com/kinoplan/sbt-clean")),
    licenses := Seq(License("Apache-2.0", uri("https://opensource.org/licenses/Apache-2.0"))),
    developers := List(Developer("kinoplan", "Kinoplan", "job@kinoplan.ru", uri("https://kinoplan.tech"))),
    scmInfo := Some(
      ScmInfo(
        uri("https://github.com/kinoplan/sbt-clean"),
        "scm:git:git@github.com:kinoplan/sbt-clean.git"
      )
    )
  )
)

Global / onChangedBuildSource := ReloadOnSourceChanges
// format: on
