addCommandAlias("fix", ";clean;scalafixAll;")
addCommandAlias("fixCheck", ";scalafixAll --check;")

addCommandAlias("fmt", ";clean;scalafmtSbt;scalafmtAll;")
addCommandAlias("fmtCheck", ";scalafmtSbtCheck;scalafmtCheckAll;")

addCommandAlias("format", ";fix;fmt;")
addCommandAlias("formatCheck", ";clean;fixCheck;fmtCheck;")
