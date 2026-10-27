package tech.kinoplan.sbt.clean

import sbt._
import sbt.Keys._

object CleanAllPlugin extends AutoPlugin {

  object autoImport {

    @transient
    val cleanAll = taskKey[Seq[Unit]]("Task that executes clean all projects in a build")

  }

  import autoImport._

  override def projectSettings =
    Seq(cleanAll := Def.taskDyn(clean.all(ScopeFilter(inAnyProject))).value)

}
