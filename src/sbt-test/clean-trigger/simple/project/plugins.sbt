val pluginVersion = sys
  .props
  .getOrElse("plugin.version", sys.error("'plugin.version' environment variable is not set"))

addSbtPlugin("io.kinoplan" % "sbt-clean" % pluginVersion)
