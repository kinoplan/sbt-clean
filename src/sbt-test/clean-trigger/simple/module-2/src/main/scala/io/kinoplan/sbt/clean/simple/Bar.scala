package io.kinoplan.sbt.clean.simple

trait Bar[F[_]] {
  def test: F[Unit]
}
