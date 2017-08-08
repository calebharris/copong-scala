package copong

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * @author caleb
  */
@ScalaJSDefined
trait Vector1D extends js.Object {
  val x: Int
}

@ScalaJSDefined
trait Vector2D extends Vector1D {
  val y: Int
}

@ScalaJSDefined
trait PositionAndVelocity[A] extends js.Object {
  val v: A
  val pos: A
}

@ScalaJSDefined
trait GameState extends js.Object {
  val paddle: PositionAndVelocity[Vector1D]
  val ball: PositionAndVelocity[Vector2D]
}

@ScalaJSDefined
trait GameStateEvent extends js.Object {
  val name: String
  val state: GameState
}