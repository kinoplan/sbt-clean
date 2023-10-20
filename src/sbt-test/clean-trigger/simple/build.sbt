import scala.io.Source

Global / scalaVersion := "2.13.12"

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

Global / TaskKey[Unit]("check") := {
  val file = (ThisBuild / baseDirectory).value / "module-2" / "target" / "streams" / "_global" /
    "triggeredClean" / "_global" / "streams" / "in-cache"
  val source = Source.fromFile(file.getAbsolutePath)
  val content = source.mkString
  source.close()

  if (!content.contains("Bar.scala")) sys.error("unexpected content: " + content)
  ()
}

Global / onChangedBuildSource := ReloadOnSourceChanges
