package tech.kinoplan.sbt.clean.simple.api

trait Foo[F[_]] {
  def test: F[Unit]
}
