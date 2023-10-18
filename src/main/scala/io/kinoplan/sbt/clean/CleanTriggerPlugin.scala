package io.kinoplan.sbt.clean

import sbt.*
import sbt.Keys.*

object CleanTriggerPlugin extends AutoPlugin {

  object autoImport {

    lazy val cleanTriggerSources =
      settingKey[Seq[File]]("Sources used to determine whether clean should be run")

    lazy val triggeredClean =
      taskKey[Unit]("Task that executes the clean task based on whether the sources is changed")

  }

  import autoImport._

  override lazy val buildSettings = Seq(cleanTriggerSources := Nil)

  override def projectSettings = Seq(
    triggeredClean := {
      val files = cleanTriggerSources
        .value
        .flatMap(source =>
          if (source.isDirectory) (source ** "*").get().filterNot(_.isDirectory)
          else Seq(source)
        )
        .toSet

      if (files.nonEmpty) {
        val taskStreams = streams.value: @sbtUnchecked

        val cleanTarget = (inReport: ChangeReport[File], outReport: ChangeReport[File]) => {
          IO.delete(target.value)

          taskStreams
            .log
            .info(
              s"Clean target directory and recompile project `${name.value}` due to detection " +
                s"changes in the selected sources: \n" +
                inReport.modified.map(_.getPath).mkString("\n")
            )

          outReport.checked
        }

        val cacheDifferenceAction = FileFunction.cached(taskStreams.cacheStoreFactory)(cleanTarget)

        cacheDifferenceAction(files)
      }
    },
    Compile / compile :=
      (Compile / compile).dependsOn(triggeredClean).value
  )

}
