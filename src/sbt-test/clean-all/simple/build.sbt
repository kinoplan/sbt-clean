Global / scalaVersion := "2.13.12"

lazy val root = project.in(file(".")).aggregate(module1, module2)

lazy val module1 = project.in(file("module-1"))

lazy val module2 = project.in(file("module-2")).enablePlugins(CleanAllPlugin)

Global / onChangedBuildSource := ReloadOnSourceChanges
