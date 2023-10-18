import org.typelevel.scalacoptions.ScalacOptions

name := "sbt-clean"
description := "A set of plugins for sbt on advanced clean management"

enablePlugins(SbtPlugin)

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
    sonatypeCredentialHost := Sonatype.sonatype01,
    versionScheme := Some(VersionScheme.EarlySemVer),
    organization := "io.kinoplan",
    homepage := Some(url("https://github.com/kinoplan/sbt-clean")),
    licenses := Seq("Apache-2.0" -> url("https://opensource.org/licenses/Apache-2.0")),
    developers := List(Developer("kinoplan", "Kinoplan", "job@kinoplan.ru", url("https://kinoplan.tech"))),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/kinoplan/sbt-clean"),
        "scm:git:git@github.com:kinoplan/sbt-clean.git"
      )
    )
  )
)

Global / onChangedBuildSource := ReloadOnSourceChanges
// format: on
