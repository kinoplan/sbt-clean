Global / scalaVersion := "2.13.18"

lazy val root = project.in(file(".")).aggregate(module1, module2)

lazy val module1 = project.in(file("module-1"))

lazy val module2 = project
  .in(file("module-2"))
  .enablePlugins(CleanTriggerPlugin)
  .settings(
    cleanTriggerSources :=
      Seq(
        (ThisBuild / baseDirectory).value / "module-1" / "src" / "main" / "scala" / "tech" /
          "kinoplan" / "sbt" / "clean" / "simple" / "api"
      )
  )

@transient val markTarget = taskKey[Unit]("Create a marker to detect cleanup")
@transient val checkTarget = taskKey[Unit]("Check whether the target marker exists")
@transient val checkTargetAbsent = taskKey[Unit]("Check that clean removed the target marker")

module2 / markTarget := IO.touch((module2 / target).value / "clean-marker")
module2 / checkTarget :=
  assert(((module2 / target).value / "clean-marker").exists, "Target marker is missing")
module2 / checkTargetAbsent :=
  assert(!((module2 / target).value / "clean-marker").exists, "Clean did not remove the target marker")

Global / onChangedBuildSource := ReloadOnSourceChanges
