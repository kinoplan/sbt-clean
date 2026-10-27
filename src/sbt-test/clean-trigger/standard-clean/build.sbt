import sbtcompat.PluginCompat._

scalaVersion := "2.13.18"

enablePlugins(CleanTriggerPlugin)

cleanTriggerSources := Seq(baseDirectory.value / "trigger.txt")
cleanFiles += baseDirectory.value / "extra-output"
cleanKeepFiles += target.value / "keep.txt"

clean := Def.uncached {
  clean.value
  if ((baseDirectory.value / "fail-clean").exists) sys.error("intentional clean failure")
  IO.touch(baseDirectory.value / "clean-called")
}

@transient val prepare = taskKey[Unit]("Create files affected by clean settings")
@transient val checkKept = taskKey[Unit]("Check that cleanKeepFiles was respected")
@transient val changeTrigger = taskKey[Unit]("Recreate the watched file")

prepare := {
  IO.touch(target.value / "keep.txt")
  IO.touch(baseDirectory.value / "extra-output")
}
checkKept := assert((target.value / "keep.txt").exists, "cleanKeepFiles was ignored")
changeTrigger := IO.write(baseDirectory.value / "trigger.txt", "changed")
