package tech.kinoplan.sbt.clean

import sbt._
import sbt.Keys._

// Required by Def.uncached on sbt 1; sbt 2 provides it natively.
// scalafix:off
import sbtcompat.PluginCompat._
// scalafix:on

object CleanTriggerPlugin extends AutoPlugin {

  object autoImport {

    lazy val cleanTriggerSources =
      settingKey[Seq[File]]("Sources used to determine whether clean should be run")

    @transient
    lazy val triggeredClean =
      taskKey[Unit]("Task that executes the clean task based on whether the sources is changed")

  }

  import autoImport._

  override lazy val buildSettings = Seq(cleanTriggerSources := Nil)

  override def projectSettings = Seq(
    triggeredClean :=
      Def
        .taskDyn {
          val sources = cleanTriggerSources.value
          val files = sources
            .flatMap(source =>
              if (source.isDirectory) (source ** "*").get().filterNot(_.isDirectory)
              else Seq(source)
            )
            .toSet

          if (sources.isEmpty) Def.task(())
          else {
            val taskStreams = streams.value: @sbtUnchecked
            val cacheStore = taskStreams.cacheStoreFactory.make("in-cache")
            val current = FileInfo.lastModified(files)
            val previous = cacheStore.read(default = FilesInfo.empty[ModifiedFileInfo])
            val changedFiles =
              ((previous.files -- current.files) ++ (current.files -- previous.files)).map(_.file)

            if (changedFiles.isEmpty) Def.task(())
            else {
              val projectName = name.value
              Def.task {
                val _ = clean.value
                // clean may remove the cache too; record inputs only after it succeeds.
                cacheStore.write(current)
                taskStreams
                  .log
                  .info(
                    s"Clean and recompile project `$projectName` due to changes in the selected sources: \n" +
                      changedFiles.toSeq.map(_.getPath).sorted.mkString("\n")
                  )
              }
            }
          }
        }
        .value,
    Compile / compile := Def.uncached((Compile / compile).dependsOn(triggeredClean).value)
  )

}
