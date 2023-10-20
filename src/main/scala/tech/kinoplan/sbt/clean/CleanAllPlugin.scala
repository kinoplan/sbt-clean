package tech.kinoplan.sbt.clean

import sbt.*
import sbt.Keys.*

object CleanAllPlugin extends AutoPlugin {

  object autoImport {
    val cleanAll = taskKey[Seq[Unit]]("Task that executes clean all projects in a build")
  }

  import autoImport._

  override def projectSettings =
    Seq(cleanAll := Def.taskDyn(clean.all(ScopeFilter(inAnyProject))).value)

}
