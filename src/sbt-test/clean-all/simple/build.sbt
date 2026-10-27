@transient val markTarget = taskKey[Unit]("Create a marker in the project target")
@transient val checkTarget = taskKey[Unit]("Check whether the target marker exists")
@transient val checkTargetAbsent = taskKey[Unit]("Check that clean removed the target marker")

lazy val markerSettings = Seq(
  markTarget := IO.touch(target.value / "clean-marker"),
  checkTarget := assert((target.value / "clean-marker").exists, "Target marker is missing"),
  checkTargetAbsent :=
    assert(!(target.value / "clean-marker").exists, "Clean did not remove the target marker")
)

Global / scalaVersion := "2.13.18"

lazy val root = project
  .in(file("."))
  .aggregate(module1, module2)
  .settings(markerSettings)
  .settings(
    markTarget / aggregate := false,
    checkTarget / aggregate := false,
    checkTargetAbsent / aggregate := false
  )

lazy val module1 = project.in(file("module-1")).settings(markerSettings)

lazy val module2 = project.in(file("module-2")).enablePlugins(CleanAllPlugin).settings(markerSettings)

Global / onChangedBuildSource := ReloadOnSourceChanges
